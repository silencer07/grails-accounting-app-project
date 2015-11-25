package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.AccountCategory
import com.admu.accountinggroup.domain.TransactionDocumentTemp
import com.admu.accountinggroup.domain.TransactionTemp
import com.admu.accountinggroup.domain.Type
import com.gmongo.GMongo
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

import com.mongodb.util.JSON as MongoJSON

/**
 * Copy from last deliverable
 */
@Transactional
class ImporterService {

    private static final String TYPES_CSV = "types.csv";
    private static final String ACCOUNT_CATEGORIES_CSV = "account_categories.csv";
    private static final String ACCOUNTS_CSV = "accounts.csv";

    def grailsApplication
    def mongo

    private void importTypes(){
        def inputStream = this.class.getClassLoader().getResourceAsStream(TYPES_CSV)
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new InputStreamReader(inputStream));
        for (CSVRecord record : records) {
            def type = new Type();
            type.id = record.get(0);
            type.name = record.get(1);
            type.description = record.get(2);
            type.save()
        }
    }

    private void importAccountCategories(){
        def inputStream = this.class.getClassLoader().getResourceAsStream(ACCOUNT_CATEGORIES_CSV)
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new InputStreamReader(inputStream));
        for (CSVRecord record : records) {
            def accountCategory = new AccountCategory();
            accountCategory.code = Integer.parseInt(record.get(0));
            accountCategory.name = record.get(1);
            accountCategory.description = record.get(2);

            def typeId = String.valueOf(record.get(0).charAt(0)).toLong();
            typeId = typeId <= 4 ? typeId : 4;
            accountCategory.type = Type.get(typeId);
            accountCategory.save()
        }
    }

    private void importAccounts(){
        def inputStream = this.class.getClassLoader().getResourceAsStream(ACCOUNTS_CSV)
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new InputStreamReader(inputStream));
        for (CSVRecord record : records) {
            def account = new Account()
            account.codePrefix = AccountCategory.findByCode(record.get(0).toLong());
            account.codeSuffix = record.get(1).toInteger()
            account.name = record.get(2);
            account.description = record.get(3);
            account.side = Side.valueOf(record.get(4));
            account.save()
        }
    }

    void startImport(){
        dropMongoDBCache()
        importTypes();
        importAccountCategories();
        importAccounts();
        createTempTransactionAccountEntries()
    }

    private def dropMongoDBCache() {
        def db = mongo.getDB("accountinggroup-web")
        db.dropDatabase()
    }

    private void createTempTransactionAccountEntries(){
        def debitAccounts = Account.findAllBySide(Side.DR)
        def creditAccounts = Account.findAllBySide(Side.CR)

        TransactionDocumentTemp doc = new TransactionDocumentTemp()
        doc.documentDate = new Date() - 6
        doc.reference = "Test Entry"
        doc.balance = 1000

        def debit = new TransactionTemp()
        debit.transactionDocument = doc
        debit.amount = 1000
        debit.comment = "Test Comment"
        debit.description = "Test Desc"
        debit.postingKey = Side.DR
        debit.account = debitAccounts[5]
        doc.addToTransactions(debit)

        def credit = new TransactionTemp(debit.properties)
        credit.postingKey = Side.CR
        credit.account = creditAccounts[5]
        credit.amount = 500
        doc.addToTransactions(credit)

        def credit2 = new TransactionTemp(credit.properties)
        credit2.account = creditAccounts[6]
        doc.addToTransactions(credit2)

        doc.save()

        def gmongo = new GMongo()
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        def dbObject = (DBObject) MongoJSON.parse((doc as JSON).toString())
        db.transactions << dbObject

        addOtherEntries(debitAccounts, creditAccounts)
    }

    private def addOtherEntries(debitAccounts, creditAccounts){
        def gmongo = new GMongo()
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        for(int i = 0; i < 5 ;i++) {
            def amount = (i + 1) * 500
            def doc = new TransactionDocumentTemp()
            doc.documentDate = new Date() - (5 - i)
            doc.reference = "Test Entry ${i}"
            doc.balance = amount

            def debit = new TransactionTemp()
            debit.transactionDocument = doc
            debit.amount = amount
            debit.comment = "Test Comment ${i}"
            debit.description = "Test Desc ${i}"
            debit.postingKey = Side.DR
            debit.account = debitAccounts[i]
            doc.addToTransactions(debit)

            def credit = new TransactionTemp(debit.properties)
            credit.postingKey = Side.CR
            credit.account = creditAccounts[i]
            doc.addToTransactions(credit)

            doc.save()

            def dbObject = (DBObject) MongoJSON.parse((doc as JSON).toString())
            db.transactions << dbObject
        }
    }
}
