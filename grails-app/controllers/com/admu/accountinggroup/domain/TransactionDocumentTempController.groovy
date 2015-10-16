package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransactionDocumentTempController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TransactionDocumentTemp.list(params), model:[transactionDocumentTempCount: TransactionDocumentTemp.count()]
    }

    def show(TransactionDocumentTemp transactionDocumentTemp) {
        respond transactionDocumentTemp
    }

    def create() {
        respond new TransactionDocumentTemp(params)
    }

    @Transactional
    def save(TransactionDocumentTemp transactionDocumentTemp) {
        if (transactionDocumentTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionDocumentTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionDocumentTemp.errors, view:'create'
            return
        }

        transactionDocumentTemp.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transactionDocumentTemp.label', default: 'TransactionDocumentTemp'), transactionDocumentTemp.id])
                redirect transactionDocumentTemp
            }
            '*' { respond transactionDocumentTemp, [status: CREATED] }
        }
    }

    def edit(TransactionDocumentTemp transactionDocumentTemp) {
        respond transactionDocumentTemp
    }

    @Transactional
    def update(TransactionDocumentTemp transactionDocumentTemp) {
        if (transactionDocumentTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionDocumentTemp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionDocumentTemp.errors, view:'edit'
            return
        }

        transactionDocumentTemp.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transactionDocumentTemp.label', default: 'TransactionDocumentTemp'), transactionDocumentTemp.id])
                redirect transactionDocumentTemp
            }
            '*'{ respond transactionDocumentTemp, [status: OK] }
        }
    }

    @Transactional
    def delete(TransactionDocumentTemp transactionDocumentTemp) {

        if (transactionDocumentTemp == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transactionDocumentTemp.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'transactionDocumentTemp.label', default: 'TransactionDocumentTemp'), transactionDocumentTemp.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionDocumentTemp.label', default: 'TransactionDocumentTemp'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
