package model;

import java.time.LocalDate;

public class BorrowRecord {
    private int recordId;
    private int userId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean renewed;
    private double fineIncurred;
    private boolean returnStatus; 

    public BorrowRecord() {}

    public BorrowRecord(int recordId, int userId, int bookId, LocalDate borrowDate, LocalDate dueDate,
                        LocalDate returnDate, boolean renewed, double fineIncurred) {
        this.recordId = recordId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.renewed = renewed;
        this.fineIncurred = fineIncurred;
        this.returnStatus = (returnDate == null) ? "Not Returned" : "Returned"; 
    }

    // Getters & Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate=dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.returnStatus=(returnDate == null) ? "Not Returned" : "Returned"; 
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }

    public double getFineIncurred() {
        return fineIncurred;
    }

    public void setFineIncurred(double fineIncurred) {
        this.fineIncurred=fineIncurred;
    }

    public boolean getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus=returnStatus;
    }

    @Override
    public String toString() {
        return "Borrow Record Details:\n" +
               "Record ID       : " + recordId + "\n" +
               "User ID         : " + userId + "\n" +
               "Book ID         : " + bookId + "\n" +
               "Borrow Date     : " + borrowDate + "\n" +
               "Due Date        : " + dueDate + "\n" +
               "Return Date     : " + returnDate + "\n" +
               "Return Status   : " + returnStatus + "\n" +
               "Renewed         : " + renewed + "\n" +
               "Fine Incurred   : ₹" + fineIncurred;
    }
}
