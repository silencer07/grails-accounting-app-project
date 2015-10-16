package com.admu.accountinggroup.domain

import grails.test.mixin.*
import spock.lang.*

@TestFor(TransactionDocumentController)
@Mock(TransactionDocument)
class TransactionDocumentControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.transactionDocumentList
            model.transactionDocumentCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.transactionDocument!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def transactionDocument = new TransactionDocument()
            transactionDocument.validate()
            controller.save(transactionDocument)

        then:"The create view is rendered again with the correct model"
            model.transactionDocument!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            transactionDocument = new TransactionDocument(params)

            controller.save(transactionDocument)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/transactionDocument/show/1'
            controller.flash.message != null
            TransactionDocument.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def transactionDocument = new TransactionDocument(params)
            controller.show(transactionDocument)

        then:"A model is populated containing the domain instance"
            model.transactionDocument == transactionDocument
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def transactionDocument = new TransactionDocument(params)
            controller.edit(transactionDocument)

        then:"A model is populated containing the domain instance"
            model.transactionDocument == transactionDocument
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/transactionDocument/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def transactionDocument = new TransactionDocument()
            transactionDocument.validate()
            controller.update(transactionDocument)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.transactionDocument == transactionDocument

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            transactionDocument = new TransactionDocument(params).save(flush: true)
            controller.update(transactionDocument)

        then:"A redirect is issued to the show action"
            transactionDocument != null
            response.redirectedUrl == "/transactionDocument/show/$transactionDocument.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/transactionDocument/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def transactionDocument = new TransactionDocument(params).save(flush: true)

        then:"It exists"
            TransactionDocument.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(transactionDocument)

        then:"The instance is deleted"
            TransactionDocument.count() == 0
            response.redirectedUrl == '/transactionDocument/index'
            flash.message != null
    }
}
