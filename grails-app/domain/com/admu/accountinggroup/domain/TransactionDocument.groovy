package com.admu.accountinggroup.domain

class TransactionDocument {

    BigDecimal balance
    Date documentDate
    String documentNumber
    Date postingDate = new Date()
    String reference
    boolean voidStatus

    static constraints = {
        balance nullable: false
        documentDate nullable: false
        documentNumber nullable: false, unique: true
        postingDate nullable: false
        reference nullable: true
    }

    static mapping = {
        table 'transaction_documents'
    }

    static hasMany = [transactions:Transaction]
}
