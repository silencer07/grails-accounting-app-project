package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransactionTempController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TransactionTemp.list(params), model:[transactionTempCount: TransactionTemp.count()]
    }

    def show(TransactionTemp transactionTemp) {
        respond transactionTemp
    }

    def create() {
        respond new TransactionTemp(params)
    }

    @Transactional
    def save(TransactionTemp transactionTemp) {
        if (transactionTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionTemp.errors, view:'create'
            return
        }

        transactionTemp.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transactionTemp.label', default: 'TransactionTemp'), transactionTemp.id])
                redirect transactionTemp
            }
            '*' { respond transactionTemp, [status: CREATED] }
        }
    }

    def edit(TransactionTemp transactionTemp) {
        respond transactionTemp
    }

    @Transactional
    def update(TransactionTemp transactionTemp) {
        if (transactionTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionTemp.errors, view:'edit'
            return
        }

        transactionTemp.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transactionTemp.label', default: 'TransactionTemp'), transactionTemp.id])
                redirect transactionTemp
            }
            '*'{ respond transactionTemp, [status: OK] }
        }
    }

    @Transactional
    def delete(TransactionTemp transactionTemp) {

        if (transactionTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transactionTemp.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'transactionTemp.label', default: 'TransactionTemp'), transactionTemp.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionTemp.label', default: 'TransactionTemp'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
