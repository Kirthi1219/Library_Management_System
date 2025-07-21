package service;

import dao.BorrowDAO;
import dao.BookDAO;
import model.BorrowRecord;
import model.Book;

import java.time.LocalDate;
import java.util.List;

public class BorrowService {
    private final BorrowDAO borrowRecordDAO;
    private final BookDAO bookDAO;
    private final FineCalculator fineCalculator;

    private static final int MAX_BORROW_LIMIT = 4;
    private static final int BORROW_DAYS = 5;
    private static final int RENEW_DAYS = 3;

    public BorrowService(BorrowDAO borrowRecordDAO, BookDAO bookDAO, FineCalculator fineCalculator) {
        this.borrowRecordDAO =borrowRecordDAO;
        this.bookDAO=bookDAO;
        this.fineCalculator=fineCalculator;
    }

    // Borrow a book
    public void borrowBook(int userId, int bookId) {
        int booksBorrowed=borrowRecordDAO.countActiveBorrowedBooks(userId);
        if (booksBorrowed>=MAX_BORROW_LIMIT) {
            System.out.println("Cannot borrow more than " + MAX_BORROW_LIMIT + " books");
            return;
        }

        Book book=bookDAO.getBookById(bookId);
        if (book == null || !book.isAvailable()) {
            System.out.println("Book is not available for borrowing");
            return;
        }

        LocalDate today=LocalDate.now();
        LocalDate dueDate=today.plusDays(BORROW_DAYS);

        BorrowRecord record=new BorrowRecord(0, userId, bookId, today, dueDate, null, false, 0);
        borrowRecordDAO.insertBorrowRecord(record);
        bookDAO.decrementAvailableCopies(bookId);
        System.out.println("Book borrowed successfully! Due date: " + dueDate);
    }

    // Renew a book (once per book only)
    public void renewBook(int userId,int bookId) {
        BorrowRecord record=borrowRecordDAO.getActiveBorrowRecord(userId,bookId);
        if (record==null) {
            System.out.println("No active borrow record found");
            return;
        }
        if (record.isRenewed()){
            System.out.println("Book has already been renewed once");
            return;
        }
        record.setDueDate(record.getDueDate().plusDays(RENEW_DAYS));
        record.setRenewed(true);
        borrowRecordDAO.updateBorrowRecord(record);
        System.out.println("Book renewed successfully! New due date: " + record.getDueDate());
    }

    // Return a book
    public void returnBook(int userId,int bookId) {
        BorrowRecord record=borrowRecordDAO.getActiveBorrowRecord(userId, bookId);
        if (record==null) {
            System.out.println("No active borrow record found");
            return;
        }

        LocalDate today=LocalDate.now();
        double fine=fineCalculator.calculateFine(record.getDueDate(), today);

        record.setReturnDate(today);
        record.setFineIncurred(fine);
        record.setReturnStatus(true);
        borrowRecordDAO.updateBorrowRecord(record);
        bookDAO.incrementAvailableCopies(bookId);

        System.out.println("Book returned successfully");
        if (fine>0) {
            System.out.println("Late return fine: ₹" + fine);
        }
    }

    // Display borrowed books of a user
    public void viewUserBorrowedBooks(int userId){
        List<BorrowRecord> records=borrowRecordDAO.getBorrowedBooksByUser(userId);
        if (records.isEmpty()) {
            System.out.println("No active borrowed books");
            return;
        }

        for (BorrowRecord r:records) {
            System.out.println(r);
        }
    }
}
