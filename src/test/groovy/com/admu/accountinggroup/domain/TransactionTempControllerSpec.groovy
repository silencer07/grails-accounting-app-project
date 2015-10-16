package com.admu.accountinggroup.domain

import grails.test.mixin.*
import spock.lang.*

@TestFor(TransactionTempController)
@Mock(TransactionTemp)
class TransactionTempControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.transactionTempList
            model.transactionTempCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.transactionTemp!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def transactionTemp = new TransactionTemp()
            transactionTemp.validate()
            controller.save(transactionTemp)

        then:"The create view is rendered again with the correct model"
            model.transactionTemp!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            transactionTemp = new TransactionTemp(params)

            controller.save(transactionTemp)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/transactionTemp/show/1'
            controller.flash.message != null
            TransactionTemp.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def transactionTemp = new TransactionTemp(params)
            controller.show(transactionTemp)

        then:"A model is populated containing the domain instance"
            model.transactionTemp == transactionTemp
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def transactionTemp = new TransactionTemp(params)
            controller.edit(transactionTemp)

        then:"A model is populated containing the domain instance"
            model.transactionTemp == transactionTemp
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/transactionTemp/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def transactionTemp = new TransactionTemp()
            transactionTemp.validate()
            controller.update(transactionTemp)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.transactionTemp == transactionTemp

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            transactionTemp = new TransactionTemp(params).save(flush: true)
            controller.update(transactionTemp)

        then:"A redirect is issued to the show action"
            transactionTemp != null
            response.redirectedUrl == "/transactionTemp/show/$transactionTemp.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/transactionTemp/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def transactionTemp = new TransactionTemp(params).save(flush: true)

        then:"It exists"
            TransactionTemp.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(transactionTemp)

        then:"The instance is deleted"
            TransactionTemp.count() == 0
            response.redirectedUrl == '/transactionTemp/index'
            flash.message != null
    }
}
