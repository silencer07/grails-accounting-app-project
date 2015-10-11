package com.admu.accountinggroup.domain

class Type {

    String name
    String description

    static constraints = {
        name unique: true, nullable: false
    }

    static mapping = {
        table 'types'
        version false
        id type: 'long', sqlType: 'serial', generator:'sequence', params:[sequence:'type_id_seq']
        description type: 'text'
    }

}
