import com.admu.accountinggroup.domain.TransactionDocumentTemp
import com.admu.accountinggroup.domain.TransactionTemp
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer

def exclusionList = ['id','class', 'account']

// Place your Spring DSL code here
beans = {

    transactionDocumentTempCollectionRenderer(JsonCollectionRenderer, TransactionDocumentTemp) {
        excludes = exclusionList
    }

    transactionDocumentTempRenderer(JsonRenderer, TransactionDocumentTemp) {
        excludes = exclusionList
    }

    transactionTempCollectionRenderer(JsonCollectionRenderer, TransactionTemp) {
        excludes = exclusionList
    }

    transactionTempRenderer(JsonRenderer, TransactionTemp) {
        excludes = exclusionList
    }
}