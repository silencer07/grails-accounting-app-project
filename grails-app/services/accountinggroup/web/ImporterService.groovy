package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.AccountCategory
import com.admu.accountinggroup.domain.TransactionDocumentTemp
import com.admu.accountinggroup.domain.TransactionTemp
import com.admu.accountinggroup.domain.Type
import grails.transaction.Transactional
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord

/**
 * Copy from last deliverable
 */
@Transactional
class ImporterService {

    private static final String TYPES_CSV = "types.csv";
    private static final String ACCOUNT_CATEGORIES_CSV = "account_categories.csv";
    private static final String ACCOUNTS_CSV = "accounts.csv";

    def grailsApplication

    private void importTypes(){
        File file = grailsApplication.mainContext.getResource(TYPES_CSV).file
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new FileReader(file));
        for (CSVRecord record : records) {
            def type = new Type();
            type.id = record.get(0);
            type.name = record.get(1);
            type.description = record.get(2);
            type.save()
        }
    }

    private void importAccountCategories(){
        File file = grailsApplication.mainContext.getResource(ACCOUNT_CATEGORIES_CSV).file
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new FileReader(file));
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
        File file = grailsApplication.mainContext.getResource(ACCOUNTS_CSV).file
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.parse(new FileReader(file));
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
        importTypes();
        importAccountCategories();
        importAccounts();
        createTempTransactionAccountEntries()
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

        addOtherEntries(debitAccounts, creditAccounts)
    }

    private def addOtherEntries(debitAccounts, creditAccounts){
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
        }
    }
}
