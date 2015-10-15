package com.admu.accountinggroup.domain

import com.admu.accountinggroup.Side

class Account {

    AccountCategory codePrefix
    int codeSuffix
    String description
    String name
    Side side

    static constraints = {
        codePrefix nullable: false
        codeSuffix nullable: false , validator: mustBeUniquePrefixSuffixCombo
        name unique: true, nullable: false
        side nullable: false
    }

    static mustBeUniquePrefixSuffixCombo = { suffix, instance ->
        return !Account.findByCodePrefixAndCodeSuffix(instance.prefix, suffix)
    }

    static mapping = {
        table 'accounts'
        version false
        id type: 'long', sqlType: 'serial'
        codePrefix column: 'code_prefix', type: 'BigInteger', sqlType: 'int4'
        description type: 'text'
        side sqlType: 'bpchar', type: 'char'
    }

    static belongsTo = [codePrefix: AccountCategory]

    static hasMany = [transactions:Transaction]

    def getCodePrefixNumber(){
        return codePrefix.code
    }
}
