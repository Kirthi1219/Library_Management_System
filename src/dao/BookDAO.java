package dao;

import util.DBConnection;
import model.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    //Getting all books
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving all books");
            e.printStackTrace();
        }

        return books;
    }

    // Returning only available books
    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE copies_available > 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving available books");
            e.printStackTrace();
        }

        return books;
    }

    //Finding a book by ID
    public Book getBookById(int bookId) {
        Book book = null;
        String query = "SELECT * FROM Book WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book = extractBookFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving book by ID");
            e.printStackTrace();
        }

        return book;
    }

    // Adding a new book
    public boolean addBook(Book book) {
        String query = "INSERT INTO Book (title, author, status, copies_total, copies_available) VALUES (?, ?, 'available', ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getCopiesTotal());
            ps.setInt(4, book.getCopiesAvailable());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding book");
            e.printStackTrace();
        }

        return false;
    }

    // Decreasing copies when borrowed
    public void decrementAvailableCopies(int bookId) {
        String query = "UPDATE Book SET copies_available = copies_available - 1 WHERE book_id = ? AND copies_available > 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error decrementing copies");
            e.printStackTrace();
        }
    }

    // Increasing copies when returned
    public void incrementAvailableCopies(int bookId) {
        String query = "UPDATE Book SET copies_available = copies_available + 1 WHERE book_id = ? AND copies_available < copies_total";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error incrementing copies");
            e.printStackTrace();
        }
    }

    // Updating full book 
    public void updateBook(Book book) {
        String query = "UPDATE Book SET title = ?, author = ?, status = ?, copies_total = ?, copies_available = ? WHERE book_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getStatus());
            ps.setInt(4, book.getCopiesTotal());
            ps.setInt(5, book.getCopiesAvailable());
            ps.setInt(6, book.getBookId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating book");
            e.printStackTrace();
        }
    }

    //Search books by title or author 
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE title LIKE ? OR author LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(extractBookFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching books");
            e.printStackTrace();
        }

        return books;
    }
    
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        return new Book(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("status"),
            rs.getInt("copies_total"),
            rs.getInt("copies_available")
        );
    }
}
