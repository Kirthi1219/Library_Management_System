package model;

public class Book{
    private int bookId;
    private String title;
    private String author;
    private String status;
    private int copiesTotal;
    private int copiesAvailable;

    //default constructor
    public Book() {}

    //parameterised constructor
    public Book(int bookId, String title, String author, String status, int copiesTotal, int copiesAvailable) {
        this.bookId=bookId;
        this.title=title;
        this.author=author;
        this.status=status;
        this.copiesTotal=copiesTotal;
        this.copiesAvailable=copiesAvailable;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public void setCopiesTotal(int copiesTotal) {
        this.copiesTotal = copiesTotal;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

   @Override
    public String toString(){
        return "Book Details:\n" +
            "Book ID          : " + bookId + "\n" +
            "Title            : " + title + "\n" +
            "Author           : " + author + "\n" +
            "Status           : " + status + "\n" +
            "Total Copies     : " + copiesTotal + "\n" +
            "Available Copies : " + copiesAvailable;
    }
}
