package com.admu.accountinggroup.domain

import java.text.SimpleDateFormat

class TransactionDocument {

    BigDecimal balance
    Date documentDate
    Date postingDate = new Date()
    String reference
    boolean voidStatus
    String uuid = UUID.randomUUID().toString()

    static constraints = {
        balance nullable: false
        documentDate nullable: false
        postingDate nullable: false
        reference nullable: true
    }

    static mapping = {
        table 'transaction_documents'
        transactions sort: "postingKey", order : 'desc'
    }

    static hasMany = [transactions:Transaction]

    def getDocumentDateFormatted(){
        def f = new SimpleDateFormat("MMM/dd")
        return f.format(documentDate)
    }

    def getPostingDateFormatted(){
        def f = new SimpleDateFormat("MMM/dd/yyyy")
        return f.format(documentDate)
    }
}
