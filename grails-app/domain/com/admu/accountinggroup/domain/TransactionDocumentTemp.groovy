package com.admu.accountinggroup.domain

class TransactionDocumentTemp {

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

    static hasMany = [transactions:TransactionTemp]
}
