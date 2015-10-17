package com.admu.accountinggroup

import com.admu.accountinggroup.domain.Account
import grails.validation.Validateable

/**
 * Created by aldrin on 10/16/15.
 */

class TransactionEntryCommand implements Validateable{

    long accountId
    Side postingKey
    BigDecimal amount
    String description
    String comments

    static constraints = {
        accountId nullable:false, min:1L , validator : { Account.findById(it) != null }
        postingKey nullable:false
        amount nullable:false, min: BigDecimal.ONE
        description nullable:true
        comments nullable:true
    }
}
