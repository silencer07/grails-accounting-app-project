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
        redirect(url: '/')
    }
}
