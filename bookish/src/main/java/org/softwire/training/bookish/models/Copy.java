package org.softwire.training.bookish.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "copies")
public class Copy {
    private Integer id;
    private Book book;
    private User borrower;
    private Date dueDate;

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne
    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
