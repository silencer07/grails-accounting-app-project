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
        description nullable:true
    }

    static mustBeUniquePrefixSuffixCombo = { codeSuffix, instance ->
        return !Account.findByCodePrefixAndCodeSuffix(instance.codePrefix, codeSuffix)
    }

    static belongsTo = [codePrefix: AccountCategory]

    static hasMany = [transactions:Transaction]

    def getCodePrefixNumber(){
        return codePrefix.code
    }

    String toString() {
        return name
    }
}
