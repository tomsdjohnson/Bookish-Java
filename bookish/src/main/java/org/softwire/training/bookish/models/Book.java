package org.softwire.training.bookish.models;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Book entity, representing a single book in the library
 */
@Entity
@Table(name = "books")
public class Book {

    private Integer id;
    private String title;
    private String author;
    private String isbn;

    private List<Copy> copies;

    public Book() {}

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(nullable = false, length = 14)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @OneToMany(mappedBy = "book")
    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

    // Calculated fields

    @Transient
    public long getAvailableCopies() {
        return getCopies().stream()
                .filter(c -> c.getBorrower() == null)
                .count();
    }

    @Transient
    public List<Copy> getBorrowedCopies() {
        return getCopies().stream()
                .filter(c -> c.getBorrower() != null)
                .collect(Collectors.toList());
    }
}
