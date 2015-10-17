package com.admu.accountinggroup.domain

import com.admu.accountinggroup.Side
import com.admu.accountinggroup.TransactionDocumentCommand
import com.admu.accountinggroup.TransactionEntryCommand
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import grails.test.mixin.*
import spock.lang.Unroll

@TestMixin(GrailsUnitTestMixin)
@Mock(Account)
class TransactionDocumentCommandSpec extends Specification {

    Account debitAccount
    Account creditAccount

    def setup() {
        debitAccount = new Account(
                id : 1L,
                description : "debit",
                name : "debit",
                side : Side.DR
        )
        debitAccount.save(validate: false)

        creditAccount = new Account(
                id : 2L,
                description : "credit",
                name : " credit",
                side : Side.CR
        )
        creditAccount.save(validate : false)

        assert Account.count() == 2
    }

    def cleanup() {
    }

    @Unroll
    void "should be not valid when there is no transaction entry"() {
        when:
            def cmd = new TransactionDocumentCommand()
            cmd.entries = entries
            cmd.validate()
        then:
            cmd.hasErrors() == hasErrors
        where:
            entries | hasErrors
            null | true
        new ArrayList<TransactionEntryCommand>() | true
    }

    @Unroll
    void "test debit/credit balance"() {
        when:
            def cmd = new TransactionDocumentCommand()
            def debit = new TransactionEntryCommand(
                    accountId : debitAccount.id,
                    postingKey : Side.DR,
                    amount : BigDecimal.valueOf(debitAmount)
            )
            debit.validate()
            def credit = new TransactionEntryCommand(
                    accountId : creditAccount.id,
                    postingKey : Side.CR,
                    amount : BigDecimal.valueOf(creditAmount)
            )
            credit.validate()
            cmd.entries = [debit,credit]
            cmd.validate()
        then:
            credit.hasErrors() == false
            debit.hasErrors() == false
            cmd.hasErrors() == hasErrors
        where:
            creditAmount| debitAmount | hasErrors
            100 | 100 | false
            10 | 100 | true
            100 | 10 | true
    }
}
