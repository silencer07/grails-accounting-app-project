package com.admu.accountinggroup.domain

import com.admu.accountinggroup.TransactionDocumentCommand
import grails.transaction.Transactional

/**
 * This class will be the controller for T-Account entry
 */
class TransactionAccountEntryController {

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
        TransactionDocumentTemp.list().each { tempDoc ->
            def doc = new TransactionDocument()
            bindData(doc, tempDoc.properties, [exclude: ['transactions','documentDateFormatted', 'postingDateFormatted']])

            tempDoc.transactions.each { tempTrans ->
                def trans = new Transaction(tempTrans.properties)
                bindData(trans, tempTrans.properties, [exclude : ['transactionDocument', 'transactionDocumentId', 'accountId']])
                trans.transactionDocument = doc
                doc.addToTransactions(trans)
            }
            doc.save()
            tempDoc.delete()
        }
        //do approving here
        redirect(action: 'approvedEntries')
    }

    def approvedEntries(){
        return [documents : TransactionDocument.list()]
    }
}
