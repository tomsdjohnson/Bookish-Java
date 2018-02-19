package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager entityManager;

    public User getUser(String username) {
        return entityManager.find(User.class, username);
    }
}
