package com.admu.accountinggroup.domain

import com.admu.accountinggroup.TransactionDocumentCommand
import grails.transaction.Transactional

/**
 * This class will be the controller for T-Account entry
 */
class DeliverableThreeController {

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
}
