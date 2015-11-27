package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.mongodb.BasicDBObject
import com.mongodb.DBCursor
import grails.transaction.Transactional

@Transactional
class AccountSummaryService {

    public static final String DB_KEY = "accountinggroup"

    def mongo

    def createSummaryPerAccount() {
        def summaries = [:]
        Account.list().each { account ->
            def accountSummary = ['debit': BigDecimal.ZERO, 'credit' : BigDecimal.ZERO]
            account.transactions.each { transaction ->
                if(transaction.postingKey == Side.DR){
                    accountSummary.debit = transaction.amount.add(accountSummary.debit)
                } else {
                    accountSummary.credit = transaction.amount.add(accountSummary.credit)
                }
            }
            accountSummary.debit = accountSummary.debit.toString()
            accountSummary.credit = accountSummary.credit.toString()

            summaries.put(account.id.toString(), accountSummary)
        }
        return summaries
    }

    def createSummaryPerAccountInMongoDB(Map summaries){
        def db = mongo.getDB(DB_KEY)
        def summariesDbCollection = db.getCollection("summaries")
        summariesDbCollection.insert(new BasicDBObject(summaries))
    }

    DBCursor readSummaryPerAccountInMongoDB(){
        def db = mongo.getDB(DB_KEY)
        def summariesDbCollection = db.getCollection("summaries")
        return summariesDbCollection.find();
    }
}
