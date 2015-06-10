package com.smartthings.model

class Book {

    String id
    String title
    String author
    String isbn

    static mapping = {
        id generator: 'uuid2'
    }

    static constraints = {
        title nullable: false
        author nullable: false
        isbn nullable: true
    }

    void beforeInsert() {
        def lastIsbn = createCriteria().get {
            projections {
                max 'isbn'
            }
        }
        isbn = lastIsbn ? lastIsbn + 'a' : 'a'
    }

}
