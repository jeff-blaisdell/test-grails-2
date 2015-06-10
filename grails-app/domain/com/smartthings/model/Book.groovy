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

    // Causes StackOverflowException during test, but works when running app normally.
    // The implementation returned by createCriteria() differs between test vs normal app execution.
    // test impl flushes, normal app impl does not flush.
    // flushing causes issue documented here:
    // https://grails.github.io/grails-doc/2.3.11/guide/GORM.html#eventsAutoTimestamping
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
    // withNewSession usage is documented here:
    // https://grails.github.io/grails-doc/2.3.11/guide/GORM.html#eventsAutoTimestamping - beforeDelete

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
