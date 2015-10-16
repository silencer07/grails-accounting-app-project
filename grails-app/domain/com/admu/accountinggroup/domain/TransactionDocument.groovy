package com.admu.accountinggroup.domain

class TransactionDocument {

    BigDecimal balance
    java.sql.Date documentDate
    long documentNumber
    java.sql.Date postingDate
    String reference
    boolean voidStatus

    static constraints = {
        balance nullable: false
        documentDate nullable: false
        documentNumber nullable: false, unique: true
        postingDate nullable: false
    }

    static mapping = {
        table 'transaction_documents'
    }

    static hasMany = [transactions:Transaction]
}
