package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class BookService{

    private BookDAO bookDAO;
    public BookService(){
        this.bookDAO=new BookDAO();
    }

    // Add a book to the library (by librarian)
    public boolean addBook(Book book) {
        if (book.getCopiesTotal()<1) {
            System.out.println("Book must have at least one copy");
            return false;
        }
        book.setCopiesAvailable(book.getCopiesTotal());
        book.setStatus("available");
        return bookDAO.addBook(book);
    }

    // List all books
    public List<Book> getAllBooks(){
        return bookDAO.getAllBooks();
    }

    // List only available books
    public List<Book> getAvailableBooks(){
        return bookDAO.getAvailableBooks();
        
    }

    // Decrease available copy count (after borrowing)
    public void markBookAsBorrowed(int bookId) {
        bookDAO.decrementAvailableCopies(bookId);
    }

    // Increase available copy count (after return)
    public void markBookAsReturned(int bookId) {
        bookDAO.incrementAvailableCopies(bookId);
    }

    // Check if a book is available to borrow
    public boolean isBookAvailable(int bookId) {
        Book book=bookDAO.getBookById(bookId);
        return book !=null && book.getCopiesAvailable()>0;
    }

    // Display all books with status
    public void displayBooks() {
        List<Book> books=getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        for (Book book:books) {
            System.out.println(book);
            System.out.println("----------------------------");
        }
    }
}
