package org.softwire.training.bookish.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softwire.training.bookish.models.Book;
import org.softwire.training.bookish.models.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Service for handling Books
 */
@Service
@Transactional
public class BookService {

    private static final int PAGE_SIZE = 10;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EntityManager entityManager;

    public List<Book> getAllBooks(int page) {
        log.debug("Querying for all books");
        TypedQuery<Book> query = entityManager
                .createQuery("SELECT b FROM Book AS b ORDER BY b.title", Book.class);
        return getPagedResults(query, page);
    }

    public long bookPageCount() {
        long books = entityManager
                .createQuery("SELECT COUNT(b) FROM Book AS b", Long.class)
                .getSingleResult();
        return ((books - 1) / PAGE_SIZE) + 1;
    }

    public List<Book> searchBooks(String term, int page) {
        log.debug("Querying for books matching {}", term);

        TypedQuery<Book> query = entityManager
                .createQuery(
                        "SELECT b FROM Book AS b " +
                        "WHERE b.title LIKE :term OR b.author LIKE :term " +
                        "ORDER BY b.title", Book.class)
                .setParameter("term", "%" + escapeLikeSpecials(term) + "%");
        return getPagedResults(query, page);
    }

    public long searchBooksPageCount(String term) {
        long books = entityManager
                .createQuery("SELECT COUNT(b) FROM Book AS b " +
                        "WHERE b.title LIKE :term OR b.author LIKE :term", Long.class)
                .setParameter("term", "%" + escapeLikeSpecials(term) + "%")
                .getSingleResult();
        return ((books - 1) / PAGE_SIZE) + 1;
    }

    private List<Book> getPagedResults(TypedQuery<Book> query, int page) {
        return query
                .setMaxResults(PAGE_SIZE)
                .setFirstResult((page - 1) * PAGE_SIZE)
                .getResultList();
    }

    public Book getBook(Integer id) {
        log.debug("Finding book {}", id);
        return entityManager
                .find(Book.class, id);
    }

    public Book createBook(String title, String author, String isbn, Integer copies) {
        log.debug("Creating new book {}", title);

        // Create the Book entity
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        entityManager.persist(book);

        // Create Copies of the Book
        for (int i = 0; i < copies; i++) {
            Copy copy = new Copy();
            copy.setBook(book);
            entityManager.persist(copy);
        }

        // Refresh the book to pick up the copies
        entityManager.refresh(book);

        log.info("Created {} copies of book {} ({})", copies, book.getId(), title);
        return book;
    }

    /**
     * LIKE queries use _ and % as special characters (single and multi-character wildcards)
     * so they must be escaped when provided by the user.
     *
     * Since we escape with backslash, it must also be escaped first!
     */
    private String escapeLikeSpecials(String term) {
        return term
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
