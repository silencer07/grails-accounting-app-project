package accountinggroup.web

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.TransactionDocumentCommand
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.Transaction
import com.admu.accountinggroup.domain.TransactionDocument
import com.admu.accountinggroup.utils.DateUtils
import com.fasterxml.uuid.Generators
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
        list.sort {a,b -> DateUtils.convertToDate(a.documentDate) <=> DateUtils.convertToDate(b.documentDate)}
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

            def mongoDoc = findTransactionByUuid(it.uuid)
            if(!mongoDoc.synced?.toBoolean()){
                doc.balance = it.balance
                doc.documentDate = DateUtils.convertToDate(it.documentDate)
                doc.postingDate = DateUtils.convertToDate(it.postingDate)
                doc.reference = it.reference
                doc.uuid = it.uuid
                doc.voidStatus = it.voidStatus.toBoolean()

                doc.transactions?.clear()
                doc.save()
                it.transactions.each {
                    def txn = new Transaction()

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


                mongoDoc.synced = true

                def db = gmongo.getDB(AccountSummaryService.DB_KEY)
                db.transactions.update([uuid: doc.uuid], [ $set:mongoDoc ], true)
            }
        }
    }

    def findTransactionByUuid(String uuid){
        def db = gmongo.getDB(AccountSummaryService.DB_KEY)
        return db.transactions.findOne(uuid : uuid)
    }

    def updateTransactionDocument(TransactionDocumentCommand cmd){
        def doc = findTransactionByUuid(cmd.uuid)
        if(doc){
            doc.reference = cmd.reference
            doc.documentDate = DateUtils.convertToW3CXMLSchemaDateTimeString(cmd.documentDate)
            doc.transactions = []
            cmd.entries.each { entry ->
                if(entry){
                    def txn = [:]
                    txn.uuid = entry.uuid ?: Generators.timeBasedGenerator().generate().toString()
                    txn.account = [id : entry.accountId.toString()]
                    txn.postingKey = [enumType : entry.postingKey.getClass().getCanonicalName(), name : entry.postingKey.name()]
                    txn.amount = entry.amount.toString()
                    txn.description = entry.description
                    txn.comment = entry.comments

                    doc.transactions << txn
                }
            }
            doc.synced = false

            def db = gmongo.getDB(AccountSummaryService.DB_KEY)
            db.transactions.update([uuid: doc.uuid], [ $set:doc ])
        }
    }
}
