package org.softwire.training.bookish;

import org.softwire.training.bookish.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookishConsole {

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            List<Book> books = getBooks(connection);
            books.forEach(book -> System.out.println(book.toString()));
        } catch (SQLException e) {
            System.out.println("Caught SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookish?user=bookish&password=bookish&useSSL=false");
    }

    private static List<Book> getBooks(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try (ResultSet results = statement.executeQuery("SELECT * FROM books")) {
            List<Book> books = new ArrayList<>();

            while (results.next()) {
                Book book = new Book();
                book.setId(results.getInt("id"));
                book.setTitle(results.getString("title"));
                book.setAuthor(results.getString("author"));
                book.setIsbn(results.getString("isbn"));
                books.add(book);
            }

            return books;
        }
    }
}
