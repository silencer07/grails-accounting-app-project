package com.admu.accountinggroup.domain

import accountinggroup.web.SyncJob
import com.admu.accountinggroup.BookCommand
import com.admu.accountinggroup.Side
import com.admu.accountinggroup.TransactionDocumentCommand
import com.admu.accountinggroup.TransactionEntryCommand
import com.admu.accountinggroup.utils.DateUtils

class DeliverableThreeController {

    def mongoDBService

    def index() {
        return [documents : mongoDBService.findAllTransactions()]
    }

    def entryAdd(){

    }

    def saveTransactionEntry(TransactionDocumentCommand cmd ){
        cmd.entries = cmd.entries.grep()
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
        if (cmd.hasErrors()) {
            render view: 'entryAdd', model: [cmd : cmd, update: true]
            return
        }

        cmd.entries = cmd.entries.grep()

        mongoDBService.updateTransactionDocument(cmd)
        flash.message = "Update Successful...."
        render view: 'entryAdd', model: [cmd : cmd, update: true]
    }

    def details(){
        def doc = mongoDBService.findTransactionByUuid(params.uuid)
        if(doc){
            def cmd = new TransactionDocumentCommand()
            cmd.uuid = doc.uuid
            cmd.reference = doc.reference
            cmd.documentDate = DateUtils.convertToDate(doc.documentDate)

            doc.transactions.each {
                def txn = new TransactionEntryCommand()
                txn.accountId = it.account.id.toLong()
                txn.postingKey = Side.valueOf(it.postingKey.name)
                txn.amount = it.amount.toBigDecimal()
                txn.description = it.description
                txn.comments = it.comment
                txn.uuid = it.uuid
                cmd.entries << txn
            }

            render view: 'entryAdd', model: [cmd : cmd, update: true]
        } else {
            redirect action: 'index'
        }
    }

    def scheduleThreeMinutesFromNow(){
        SyncJob.schedule(3 * 1000 * 60, 1, [:])
        flash.message = "Sync Schedule Three Minutes From Now..."
        redirect action: "index"
    }

    def reverseEntry(){

    }

    def performReverseEntry(BookCommand cmd){
        if(cmd.validate() && cmd.exists(mongoDBService)){
            mongoDBService.reverse(cmd)
            flash.message = "Reverse Entry Saved..."
        }

        render view: 'reverseEntry', model: [cmd : cmd]
    }
}
