package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransactionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Transaction.list(params), model:[transactionCount: Transaction.count()]
    }

    def show(Transaction transaction) {
        respond transaction
    }

    def create() {
        respond new Transaction(params)
    }

    @Transactional
    def save(Transaction transaction) {
        if (transaction == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaction.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaction.errors, view:'create'
            return
        }

        transaction.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transaction.label', default: 'Transaction'), transaction.id])
                redirect transaction
            }
            '*' { respond transaction, [status: CREATED] }
        }
    }

    def edit(Transaction transaction) {
        respond transaction
    }

    @Transactional
    def update(Transaction transaction) {
        if (transaction == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaction.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaction.errors, view:'edit'
            return
        }

        transaction.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transaction.label', default: 'Transaction'), transaction.id])
                redirect transaction
            }
            '*'{ respond transaction, [status: OK] }
        }
    }

    @Transactional
    def delete(Transaction transaction) {

        if (transaction == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transaction.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'transaction.label', default: 'Transaction'), transaction.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transaction.label', default: 'Transaction'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
