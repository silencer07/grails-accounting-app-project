package com.admu.accountinggroup.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AccountCategoryControllerController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccountCategoryController.list(params), model:[accountCategoryControllerCount: AccountCategoryController.count()]
    }

    def show(AccountCategoryController accountCategoryController) {
        respond accountCategoryController
    }

    def create() {
        respond new AccountCategoryController(params)
    }

    @Transactional
    def save(AccountCategoryController accountCategoryController) {
        if (accountCategoryController == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCategoryController.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountCategoryController.errors, view:'create'
            return
        }

        accountCategoryController.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountCategoryController.label', default: 'AccountCategoryController'), accountCategoryController.id])
                redirect accountCategoryController
            }
            '*' { respond accountCategoryController, [status: CREATED] }
        }
    }

    def edit(AccountCategoryController accountCategoryController) {
        respond accountCategoryController
    }

    @Transactional
    def update(AccountCategoryController accountCategoryController) {
        if (accountCategoryController == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountCategoryController.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountCategoryController.errors, view:'edit'
            return
        }

        accountCategoryController.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountCategoryController.label', default: 'AccountCategoryController'), accountCategoryController.id])
                redirect accountCategoryController
            }
            '*'{ respond accountCategoryController, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountCategoryController accountCategoryController) {

        if (accountCategoryController == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountCategoryController.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountCategoryController.label', default: 'AccountCategoryController'), accountCategoryController.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountCategoryController.label', default: 'AccountCategoryController'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
