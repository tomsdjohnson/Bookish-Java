package org.softwire.training.bookish.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    private String username;
    private String password;
    private Boolean enabled;

    private List<Copy> borrowedCopies;

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(mappedBy = "borrower")
    public List<Copy> getBorrowedCopies() {
        return borrowedCopies;
    }

    public void setBorrowedCopies(List<Copy> borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }
}
