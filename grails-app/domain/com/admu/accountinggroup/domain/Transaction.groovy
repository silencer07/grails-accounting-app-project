package com.admu.accountinggroup.domain

import com.admu.accountinggroup.Side

class Transaction {

    Account account
    BigDecimal amount
    String comment
    String description
    Side postingKey
    TransactionDocument transactionDocument


    static constraints = {
        amount nullable: false, min: BigDecimal.ZERO
        postingKey nullable: false
        account nullable: false
        transactionDocument nullable: false
        description nullable: true
        comment nullable: true
    }

    static mapping = {
        table 'transactions'
    }

    static belongsTo = [account : Account, transactionDocument : TransactionDocument]
}
