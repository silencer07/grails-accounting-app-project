package com.admu.accountinggroup

import com.fasterxml.uuid.Generators
import grails.validation.Validateable

/**
 * Created by aldrin on 12/8/15.
 */
class BookCommand implements Validateable{

    def mongoDBService

    String uuid

    static constraints = {
        uuid nullable: false
    }

    def exists(def mongoDBService){
        return mongoDBService.findTransactionByUuid(uuid) != null;
    }
}
