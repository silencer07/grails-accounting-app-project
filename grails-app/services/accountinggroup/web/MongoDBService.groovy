package accountinggroup.web

import com.gmongo.GMongo
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
}
