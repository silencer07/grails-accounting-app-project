package com.admu.accountinggroup.domain

import com.admu.accountinggroup.BookCategory
import com.fasterxml.uuid.Generators

class Book {

    String uuid = Generators.timeBasedGenerator().generate().toString()
    BookCategory category = BookCategory.JV
    boolean synced = false

    static constraints = {
        uuid nullable: false, validator: {
            return TransactionDocument.findByUuid(it) != null
        }
        category nullable: false
    }
}
