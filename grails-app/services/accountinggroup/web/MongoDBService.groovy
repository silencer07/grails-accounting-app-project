package accountinggroup.web

import com.admu.accountinggroup.domain.TransactionDocument
import com.gmongo.GMongo
import com.mongodb.DBObject
import com.mongodb.util.JSON
import grails.transaction.Transactional

@Transactional
class MongoDBService {

    def gmongo = new GMongo()

    def findAllTransactions() {
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        def cursor = db.transactions.find()
        def list = []
        while (cursor.hasNext()){
            list << cursor.next()
        }
        return list
    }

    def saveTransaction(TransactionDocument doc){
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        def dbObject = (DBObject) JSON.parse((doc as grails.converters.JSON).toString())
        db.transactions << dbObject
    }
}
