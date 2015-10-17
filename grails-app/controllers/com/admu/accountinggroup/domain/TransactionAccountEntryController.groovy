package com.admu.accountinggroup.domain

import com.admu.accountinggroup.TransactionDocumentCommand

/**
 * This class will be the controller for T-Account entry
 */
class TransactionAccountEntryController {

    def index() {}

    def saveTransactionEntry(TransactionDocumentCommand cmd ){
        if (cmd.hasErrors()) {
            render view: 'index', model: [cmd : cmd]
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

        redirect(url: '/')
    }
}
