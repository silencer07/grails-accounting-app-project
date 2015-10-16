package com.admu.accountinggroup.domain

class TransactionDocumentTemp {

    BigDecimal balance
    java.sql.Date documentDate
    java.sql.Date postingDate
    String reference
    boolean voidStatus

    static constraints = {
        balance nullable: false
        documentDate nullable: false
        postingDate nullable: false
    }

    static mapping = {
        table 'transaction_documents_temp'
    }

    static hasMany = [transactions:TransactionTemp]
}
