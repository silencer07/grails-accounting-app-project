import com.admu.accountinggroup.Side
import com.admu.accountinggroup.domain.Account
import com.admu.accountinggroup.domain.TransactionDocument
import com.admu.accountinggroup.domain.Transaction
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer

def exclusionList = ['id','class']
def auxilliaryClassInclusionList = ['id']

// Place your Spring DSL code here
beans = {

    transactionDocumentCollectionRenderer(JsonCollectionRenderer, TransactionDocument) {
        excludes = exclusionList
    }

    transactionDocumentRenderer(JsonRenderer, TransactionDocument) {
        excludes = exclusionList
    }

    transactionCollectionRenderer(JsonCollectionRenderer, Transaction) {
        excludes = exclusionList.plus(['transactionDocument'])
    }

    transactionRenderer(JsonRenderer, Transaction) {
        excludes = exclusionList.plus(['transactionDocument'])
    }

    accountCollectionRenderer(JsonCollectionRenderer, Account) {
        includes = auxilliaryClassInclusionList
    }

    accountRenderer(JsonRenderer, Account) {
        includes = auxilliaryClassInclusionList
    }
}