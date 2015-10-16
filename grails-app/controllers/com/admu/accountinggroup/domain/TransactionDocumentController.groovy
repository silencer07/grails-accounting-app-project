package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransactionDocumentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TransactionDocument.list(params), model:[transactionDocumentCount: TransactionDocument.count()]
    }

    def show(TransactionDocument transactionDocument) {
        respond transactionDocument
    }

    def create() {
        respond new TransactionDocument(params)
    }

    @Transactional
    def save(TransactionDocument transactionDocument) {
        if (transactionDocument == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionDocument.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionDocument.errors, view:'create'
            return
        }

        transactionDocument.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transactionDocument.label', default: 'TransactionDocument'), transactionDocument.id])
                redirect transactionDocument
            }
            '*' { respond transactionDocument, [status: CREATED] }
        }
    }

    def edit(TransactionDocument transactionDocument) {
        respond transactionDocument
    }

    @Transactional
    def update(TransactionDocument transactionDocument) {
        if (transactionDocument == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionDocument.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionDocument.errors, view:'edit'
            return
        }

        transactionDocument.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transactionDocument.label', default: 'TransactionDocument'), transactionDocument.id])
                redirect transactionDocument
            }
            '*'{ respond transactionDocument, [status: OK] }
        }
    }

    @Transactional
    def delete(TransactionDocument transactionDocument) {

        if (transactionDocument == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transactionDocument.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'transactionDocument.label', default: 'TransactionDocument'), transactionDocument.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionDocument.label', default: 'TransactionDocument'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
