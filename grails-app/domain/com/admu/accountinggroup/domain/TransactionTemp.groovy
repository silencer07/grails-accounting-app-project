package com.admu.accountinggroup.domain

import com.admu.accountinggroup.Side

class TransactionTemp {

    Account account
    BigDecimal amount
    String comment
    String description
    Side postingKey
    TransactionDocumentTemp transactionDocument


    static constraints = {
        amount nullable: false, min: BigDecimal.ZERO
        postingKey nullable: false
        account nullable: false
        postingKey nullable: false
        transactionDocument nullable: false
    }

    static belongsTo = [account : Account, transactionDocument : TransactionDocumentTemp]

    static mapping = {
        table 'transactions_temp'
        version false
        id type: 'long', sqlType: 'serial'
        comment type: 'text'
        description type : 'text'
        account type: 'BigInteger', sqlType: 'int4'
        transactionDocument type: 'BigInteger', sqlType: 'int4'
        postingKey sqlType: 'bpchar', type: 'char'
    }
}
