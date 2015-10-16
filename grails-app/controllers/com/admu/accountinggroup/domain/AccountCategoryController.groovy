package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AccountCategoryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccountCategory.list(params), model:[accountCategoryCount: AccountCategory.count()]
    }

    def show(AccountCategory accountCategory) {
        respond accountCategory
    }

    def create() {
        respond new AccountCategory(params)
    }

    @Transactional
    def save(AccountCategory accountCategory) {
        if (accountCategory == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCategory.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountCategory.errors, view:'create'
            return
        }

        accountCategory.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountCategory.label', default: 'AccountCategory'), accountCategory.id])
                redirect accountCategory
            }
            '*' { respond accountCategory, [status: CREATED] }
        }
    }

    def edit(AccountCategory accountCategory) {
        respond accountCategory
    }

    @Transactional
    def update(AccountCategory accountCategory) {
        if (accountCategory == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCategory.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountCategory.errors, view:'edit'
            return
        }

        accountCategory.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountCategory.label', default: 'AccountCategory'), accountCategory.id])
                redirect accountCategory
            }
            '*'{ respond accountCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountCategory accountCategory) {

        if (accountCategory == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountCategory.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountCategory.label', default: 'AccountCategory'), accountCategory.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountCategory.label', default: 'AccountCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
