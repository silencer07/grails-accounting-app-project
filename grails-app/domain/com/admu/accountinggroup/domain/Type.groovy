package com.admu.accountinggroup.domain

class Type {

    String name
    String description

    static constraints = {
        name unique: true, nullable: false
        description nullable: true
    }

    static mapping = {
        table 'types'
    }

    String toString(){
        return name;
    }
}
