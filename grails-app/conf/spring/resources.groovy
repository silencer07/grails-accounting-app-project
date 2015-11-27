import com.admu.accountinggroup.domain.TransactionDocument
import com.admu.accountinggroup.domain.Transaction
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer

def exclusionList = ['id','class']

// Place your Spring DSL code here
beans = {

    transactionDocumentCollectionRenderer(JsonCollectionRenderer, TransactionDocument) {
        excludes = exclusionList
    }

    transactionDocumentRenderer(JsonRenderer, TransactionDocument) {
        excludes = exclusionList
    }

    transactionCollectionRenderer(JsonCollectionRenderer, Transaction) {
        excludes = exclusionList
    }

    transactionRenderer(JsonRenderer, Transaction) {
        excludes = exclusionList
    }
}