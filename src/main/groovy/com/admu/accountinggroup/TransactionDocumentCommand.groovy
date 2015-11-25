package com.admu.accountinggroup

import grails.validation.Validateable

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.Factory

/**
 * Created by aldrin on 10/16/15.
 */

class TransactionDocumentCommand implements Validateable{

    String reference
    Date documentDate = new Date()
    List<TransactionEntryCommand> entries = ListUtils.lazyList([], {new TransactionEntryCommand()} as Factory)

    static constraints = {
        reference nullable:true
        documentDate nullable : false
        entries nullable : false, validator: { entries, instance ->

            List<Long> entryAccountIdList = entries*.accountId
            Set<Long> entryAccountIdSet = new HashSet<>(entryAccountIdList)

            if(entryAccountIdList.size() != entryAccountIdSet.size())
            {
                return 'duplicateAccountFound'
            }

            def empty = entries.isEmpty();

            def hasInvalidEntry = entries?.find {
                it != null && it.validate() == false;
            };

            def creditTotal = entries?.findAll {
                it != null && Side.CR.equals(it.postingKey);
            }?.sum { it?.amount ?: 0 };

            def debitTotal = entries?.findAll {
                it != null && Side.DR.equals(it.postingKey);
            }?.sum { it?.amount ?: 0 };

            if(!empty && !hasInvalidEntry && creditTotal == debitTotal) {
                return true
            }

            return 'notBalancedOrInvalid'
        }
    }

    def getBalance() {
        return entries?.findAll {
            it != null && Side.CR.equals(it.postingKey);
        }?.sum { it?.amount ?: 0 } ?: 0
    }
}
