package com.admu.accountinggroup.domain

class AccountCategory {

    int code
    String description
    String name
    Type type

    static constraints = {
        code unique: true, nullable: false
        name unique: true, nullable: false
        type nullable: false
    }

    static belongsTo = [type:Type]

    static mapping = {
        table 'account_categories'
        version false
        id type: 'long', sqlType: 'serial'
        type column:"type_id", type: 'BigInteger', sqlType: 'int4'
        description type: 'text'
    }

    static hasMany = [accounts:Account]

    static mappedBy = [accounts: 'codePrefix']
}
