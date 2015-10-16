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
        postingKey nullable: false
        transactionDocument nullable: false
    }

    static belongsTo = [account : Account, transactionDocument : TransactionDocument]
}
