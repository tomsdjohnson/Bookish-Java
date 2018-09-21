package org.softwire.training.bookish;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello");


        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost/bookish", "bookish", "bookish");


        List<Person> people = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM people")
                    .mapToBean(Person.class)
                    .list();
        });

        for (Person person: people) {
            System.out.println("Person ID: " + person.getId() + " has name: " + person.getName());
        }

    }
}
