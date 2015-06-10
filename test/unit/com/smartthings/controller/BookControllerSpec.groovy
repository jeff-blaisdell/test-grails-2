package com.smartthings.controller

import com.smartthings.model.Book
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BookController)
@Mock([Book])
class BookControllerSpec extends Specification {

    void "index should return 1 book"() {
        setup:
        Book book = new Book(title: 'ABC', author: 'Jack')

        /**
         * Call to save() causes java.lang.StackOverflowError.
         */
        book.save()

        when:
        controller.index()

        then:
        model.books.size() == 1
    }

    void "index should return 0 book"() {
        when:
        controller.index()

        then:
        model.books.size() == 0
    }

}
