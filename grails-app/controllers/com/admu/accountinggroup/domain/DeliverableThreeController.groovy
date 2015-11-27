package com.admu.accountinggroup.domain

import com.admu.accountinggroup.TransactionDocumentCommand

/**
 * This class will be the controller for T-Account entry
 */
class DeliverableThreeController {

    def mongoDBService

    def index() {
        return [documents : mongoDBService.findAllTransactions()]
    }

    def entryAdd(){

    }

    def saveTransactionEntry(TransactionDocumentCommand cmd ){
        if (cmd.hasErrors()) {
            render view: 'entryAdd', model: [cmd : cmd]
            return
        }

        def transactionDocument = new TransactionDocument()
        transactionDocument.reference = cmd.reference
        transactionDocument.documentDate = cmd.documentDate
        cmd.entries.each {
            def transaction = new Transaction()
            transaction.account = Account.get(it.accountId)
            transaction.postingKey = it.postingKey
            transaction.amount = it.amount
            transaction.description = it.description
            transaction.comment = it.comments
            transaction.transactionDocument = transactionDocument
            transactionDocument.addToTransactions(transaction)
        }
        transactionDocument.balance = cmd.balance
        mongoDBService.saveTransaction(transactionDocument)

        flash.message = "Adding T-Account Entry Successful! Scroll down to see changes...."
        redirect(action: 'index')
    }

    def sync() {
        mongoDBService.syncToDatabase()
        flash.message = "Sync Successful...."
        redirect(action: 'index')
    }

    def updateTransactionEntry(TransactionDocumentCommand cmd){
        throw new UnsupportedOperationException("not yet implemented ")
    }
}
