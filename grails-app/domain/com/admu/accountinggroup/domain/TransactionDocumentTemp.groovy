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

    static mapping = {
        table 'transaction_document_temp'
        version false
        id type: 'long', sqlType: 'serial'
        documentNumber type: 'long', sqlType: 'serial'
        reference type: 'text'
        documentDate type: 'date', sqlType: 'date'
        postingDate type: 'date', sqlType: 'date'
    }

    static hasMany = [transactions:TransactionTemp]
}
