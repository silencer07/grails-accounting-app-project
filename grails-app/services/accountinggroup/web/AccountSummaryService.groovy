package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.mongodb.BasicDBObject
import grails.transaction.Transactional

@Transactional
class AccountSummaryService {

    public static final String DB_KEY = "summaries"

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

    def createSummaryPerAccountInMongoDB(){
        def summaries = createSummaryPerAccount()
        def db = mongo.getDB("accountinggroup-web")
        def summariesDbCollection = db.getCollection(DB_KEY)
        summariesDbCollection.insert(new BasicDBObject(summaries))
    }
}