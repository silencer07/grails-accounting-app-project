package com.admu.accountinggroup.domain

import grails.test.mixin.*
import spock.lang.*

@TestFor(TransactionDocumentTempController)
@Mock(TransactionDocumentTemp)
class TransactionDocumentTempControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.transactionDocumentTempList
            model.transactionDocumentTempCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.transactionDocumentTemp!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def transactionDocumentTemp = new TransactionDocumentTemp()
            transactionDocumentTemp.validate()
            controller.save(transactionDocumentTemp)

        then:"The create view is rendered again with the correct model"
            model.transactionDocumentTemp!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            transactionDocumentTemp = new TransactionDocumentTemp(params)

            controller.save(transactionDocumentTemp)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/transactionDocumentTemp/show/1'
            controller.flash.message != null
            TransactionDocumentTemp.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def transactionDocumentTemp = new TransactionDocumentTemp(params)
            controller.show(transactionDocumentTemp)

        then:"A model is populated containing the domain instance"
            model.transactionDocumentTemp == transactionDocumentTemp
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def transactionDocumentTemp = new TransactionDocumentTemp(params)
            controller.edit(transactionDocumentTemp)

        then:"A model is populated containing the domain instance"
            model.transactionDocumentTemp == transactionDocumentTemp
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/transactionDocumentTemp/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def transactionDocumentTemp = new TransactionDocumentTemp()
            transactionDocumentTemp.validate()
            controller.update(transactionDocumentTemp)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.transactionDocumentTemp == transactionDocumentTemp

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            transactionDocumentTemp = new TransactionDocumentTemp(params).save(flush: true)
            controller.update(transactionDocumentTemp)

        then:"A redirect is issued to the show action"
            transactionDocumentTemp != null
            response.redirectedUrl == "/transactionDocumentTemp/show/$transactionDocumentTemp.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/transactionDocumentTemp/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def transactionDocumentTemp = new TransactionDocumentTemp(params).save(flush: true)

        then:"It exists"
            TransactionDocumentTemp.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(transactionDocumentTemp)

        then:"The instance is deleted"
            TransactionDocumentTemp.count() == 0
            response.redirectedUrl == '/transactionDocumentTemp/index'
            flash.message != null
    }
}
