package com.admu.accountinggroup.domain

class Type {

    String name
    String description

    static constraints = {
        name unique: true, nullable: false
    }

    String toString(){
        return name;
    }
}
