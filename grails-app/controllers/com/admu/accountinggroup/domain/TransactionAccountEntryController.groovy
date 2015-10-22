package com.admu.accountinggroup.domain

import com.admu.accountinggroup.TransactionDocumentCommand
import grails.transaction.Transactional

/**
 * This class will be the controller for T-Account entry
 */
class TransactionAccountEntryController {

    def accountSummaryService

    def index() {
        return [documents : TransactionDocumentTemp.list()]
    }

    def entryAdd(){

    }

    def saveTransactionEntry(TransactionDocumentCommand cmd ){
        if (cmd.hasErrors()) {
            render view: 'entryAdd', model: [cmd : cmd]
            return
        }

        def transactionDocument = new TransactionDocumentTemp()
        transactionDocument.reference = transactionDocument.reference
        transactionDocument.documentDate = cmd.documentDate
        cmd.entries.each {
            def transaction = new TransactionTemp()
            transaction.account = Account.get(it.accountId)
            transaction.postingKey = it.postingKey
            transaction.amount = it.amount
            transaction.description = it.description
            transaction.comment = it.comments
            transaction.transactionDocument = transactionDocument
            transactionDocument.addToTransactions(transaction)
        }
        transactionDocument.balance = cmd.balance
        transactionDocument.save()

        redirect(action: 'index')
    }

    @Transactional
    def approveTempEntries(){
        def results = [:]

        results.normalWrite = benchmarkDB({
            saveDBEntries()
        })

        Map summaries
        results.normalRead = benchmarkDB({
            summaries = accountSummaryService.createSummaryPerAccount()
        })

        results.mongoWrite = benchmarkDB({
            accountSummaryService.createSummaryPerAccountInMongoDB(summaries)
        })

        results.mongoRead = benchmarkDB({
            accountSummaryService.readSummaryPerAccountInMongoDB()
        })

        //they are the same since table view is just
        results.tableViewWrite = results.normalWrite;
        results.tableViewRead = benchmarkDB({
            //this is the view table mapped via ORM
            AccountSummary.list()
        })

        session.results = results

        redirect(action: 'approvedEntries')
    }

    private def saveDBEntries(){
        TransactionDocumentTemp.list().each { tempDoc ->
            def doc = new TransactionDocument()
            bindData(doc, tempDoc.properties, [exclude: ['transactions','documentDateFormatted', 'postingDateFormatted', 'mongo']])

            tempDoc.transactions.each { tempTrans ->
                def trans = new Transaction(tempTrans.properties)
                bindData(trans, tempTrans.properties, [exclude : ['transactionDocument', 'transactionDocumentId', 'accountId', 'mongo']])
                trans.transactionDocument = doc
                doc.addToTransactions(trans)
            }
            doc.save(flush:true)
        }

        TransactionDocumentTemp.deleteAll()
    }

    private def benchmarkDB(Closure c){
        def b = benchmark {
           c()
        }
        b.prettyPrint()

        def real
        b.eachWithIndex { it, index ->
            real = it.time.real
        }
        return real
    }

    def approvedEntries(){
        return [documents : TransactionDocument.list()]
    }

    def chart() {
    }
}
