package org.softwire.training.bookish.controllers;

import com.google.common.collect.ImmutableMap;
import org.softwire.training.bookish.models.Book;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller handling all book pages
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/books")
    ModelAndView getBooks(
            @RequestParam(defaultValue = "") String term,
            @RequestParam(defaultValue = "1") Integer page) {

        List<Book> books = term.isEmpty()
                ? bookService.getAllBooks(page)
                : bookService.searchBooks(term, page);

        long pageCount = term.isEmpty()
                ? bookService.bookPageCount()
                : bookService.searchBooksPageCount(term);

        return new ModelAndView("books", ImmutableMap.of(
                "books", books,
                "term", term,
                "page", page,
                "pageCount", pageCount));
    }

    @RequestMapping("/books/{id}")
    ModelAndView getBook(@PathVariable Integer id) {
        return new ModelAndView("book", "book", bookService.getBook(id));
    }

    @RequestMapping(value = "/books/create", method = RequestMethod.GET)
    ModelAndView createBooks() {
        return new ModelAndView("create");
    }

    @RequestMapping(value = "/books/create", method = RequestMethod.POST)
    ModelAndView createBooks(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam Integer copies) {
        Book book = bookService.createBook(title, author, isbn, copies);
        return new ModelAndView("labels", "book", book);
    }
}
