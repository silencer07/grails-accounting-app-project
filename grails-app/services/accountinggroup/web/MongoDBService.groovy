package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.TransactionEntryCommand
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.Transaction
import com.admu.accountinggroup.domain.TransactionDocument
import com.admu.accountinggroup.utils.DateUtils
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

    def syncToDatabase(){
        findAllTransactions().each {
            def doc = TransactionDocument.findByUuid(it.uuid)
            if(!doc){
                doc = new TransactionDocument()
            }

            doc.balance = it.balance
            doc.documentDate = DateUtils.convertToDate(it.documentDate)
            doc.postingDate = DateUtils.convertToDate(it.postingDate)
            doc.reference = it.reference
            doc.uuid = it.uuid
            doc.voidStatus = it.voidStatus.toBoolean()

            it.transactions.each {
                def txn = Transaction.findByUuid(it.uuid)
                if(!txn){
                    txn = new Transaction()
                }
                txn.account = Account.get(it.account.id.toLong())
                txn.amount = it.amount.toBigDecimal()
                txn.description = it.description
                txn.postingKey = Side.valueOf(it.postingKey.name)
                txn.comment = it.comment
                txn.uuid = it.uuid
                doc.addToTransactions(txn)
            }

            doc.synced = true

            doc.save()
        }
    }

    def findTransactionByUuid(String uuid){
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        return db.transactions.findOne(uuid : uuid)
    }

    def updateTransactionDocument(TransactionEntryCommand cmd){
        //update sync = false
    }
}
