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
        description nullable: true
    }

    static belongsTo = [type:Type]

    static hasMany = [accounts:Account]

    static mappedBy = [accounts: 'codePrefix']

    String toString() {
        return name
    }
}
