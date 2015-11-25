package com.admu.accountinggroup.domain

import java.text.SimpleDateFormat

class TransactionDocumentTemp {

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
        table 'transaction_documents_temp'
        transactions sort: "postingKey", order : 'desc'
    }

    static hasMany = [transactions:TransactionTemp]

    def getDocumentDateFormatted(){
        def f = new SimpleDateFormat("MMM/dd")
        return f.format(documentDate)
    }

    def getPostingDateFormatted(){
        def f = new SimpleDateFormat("MMM/dd/yyyy")
        return f.format(documentDate)
    }
}
