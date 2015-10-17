package com.admu.accountinggroup.domain

class TransactionDocument {

    BigDecimal balance
    Date documentDate
    Date postingDate = new Date()
    String reference
    boolean voidStatus

    static constraints = {
        balance nullable: false
        documentDate nullable: false
        postingDate nullable: false
        reference nullable: true
    }

    static mapping = {
        table 'transaction_documents'
    }

    static hasMany = [transactions:Transaction]
}
