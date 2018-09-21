package org.softwire.training.bookish;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello");

        //this just does its thing don't ask//
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost/test?user=bookish&password=bookish&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT");


        //getting list of people using SQL commands//
        List<Person> people = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM people")
                    .mapToBean(Person.class)
                    .list();
        });


        for (Person person: people) {
            System.out.println("Person ID: " + person.getPersonID() + " their email is: " + person.getEmail());
        }

    }
}
