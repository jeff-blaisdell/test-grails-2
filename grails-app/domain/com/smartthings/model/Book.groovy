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

    /**

    // This works.
    // During test, the createCriteria implementation differs from
    // the implementation used during the actual running of app.
    // The test implementation flushes before executing query.
    // The flush causes hibernate events to fire which result in circular
    // execution and ultimately a StackOverflowException.
    // Using withNewSession isolates the execution preventing the circular behavior.

    void beforeInsert() {
        withNewSession {
            def lastIsbn = createCriteria().get {
                projections {
                    max 'isbn'
                }
            }
            isbn = lastIsbn ? lastIsbn + 'a' : 'a'
        }
    }

    */
}
