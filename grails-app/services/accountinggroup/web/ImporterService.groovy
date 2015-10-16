package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.AccountCategory
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
    }
}
