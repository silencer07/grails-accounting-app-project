package com.admu.accountinggroup.domain

class AccountSummary {
    long accountId
    BigDecimal debit
    BigDecimal credit

    static constraints = {
    }

    static mapping = {
        table 'account_summaries'
        version false
        cache usage: 'read-only'
    }

    def getAccount(){
        return Account.get(accountId)
    }
}
