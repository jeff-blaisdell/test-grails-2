package com.smartthings.controller

import com.smartthings.model.Book

class BookController {

    def index() {
        List<Book> books = Book.list()

        render(view: 'index', model: [books: books])
    }
}
