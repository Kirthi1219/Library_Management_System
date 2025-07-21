package service;

import dao.UserDAO;
import dao.BorrowDAO;
import model.User;
import model.BorrowRecord;
import service.FineCalculator;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    private UserDAO userDAO;
    private BorrowDAO borrowDAO;
    private FineCalculator fineCalculator;

    public UserService() {
        this.userDAO=new UserDAO();
        this.borrowDAO=new BorrowDAO();
        this.fineCalculator=new FineCalculator();
    }

    // Add new user
    public boolean registerUser(String name, String phNo) {
        User user=new User();
        user.setName(name);
        user.setPhNo(phNo);
        return userDAO.addUser(user);
    }

    // Get user by ID
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    // Display all users
    // public void displayAllUsers() {
    //     List<User> users=userDAO.getAllUsers();
    //     if (users.isEmpty()) {
    //         System.out.println("No users registered");
    //         return;
    //     }
    //     for (User user:users) {
    //         System.out.println(user);
    //         System.out.println("--------------------");
    //     }
    // }

    // Display all users with their borrow records
    public void displayAllUsers() {
        List<User> users=userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered");
            return;
        }

        for (User user:users) {
            System.out.println(user); 
            List<BorrowRecord> borrowedBooks=borrowDAO.getBorrowedBooksByUser(user.getUserId());

            if (borrowedBooks.isEmpty()) {
                System.out.println("No books currently borrowed");
            } else {
                System.out.println("Borrowed Books:");
                for (BorrowRecord record : borrowedBooks) {
                    System.out.println("Book ID      : " + record.getBookId());
                    System.out.println("Borrowed On  : " + record.getBorrowDate());
                    System.out.println("Due Date     : " + record.getDueDate());
                    System.out.println("Return Status: " + (record.getReturnStatus() ? "Returned" : "Not Returned"));
                    System.out.println("Renewed      : " + (record.isRenewed() ? "Yes" : "No"));
                    double fine = 0.0;
                    if (record.getReturnStatus()) {
                        fine = FineCalculator.calculateFine(record.getDueDate(), record.getReturnDate());
                    } else {
                        fine = FineCalculator.calculateFineTillToday(record.getDueDate());
                    }
                    System.out.println("Amount Due   : Rs. " + fine);
                    // System.out.println("----------------------------");
                }
            }

            System.out.println("=============================");
        }
    }


    // Check user status (for librarian)
    public void checkUserStatus(int userId) {
        User user=userDAO.getUserById(userId);
        if (user==null) {
            System.out.println("User not found");
            return;
        }
        System.out.println(user);
        List<BorrowRecord> borrowedBooks=borrowDAO.getBorrowedBooksByUser(userId);

        if (borrowedBooks.isEmpty()) {
            System.out.println("No books currently borrowed");
            return;
        }

        System.out.println("\nBorrowed Books:");
        for (BorrowRecord record:borrowedBooks) {
            System.out.println("Book ID      : " + record.getBookId());
            System.out.println("Borrowed On  : " + record.getBorrowDate());
            System.out.println("Due Date     : " + record.getDueDate());
            System.out.println("Return Status: " + (record.getReturnStatus() ? "Returned" : "Not Returned"));
            System.out.println("Renewed      : " + (record.isRenewed() ? "Yes" : "No"));
            double fine = 0.0;
            if (record.getReturnStatus()) {
                // If returned late
                fine = FineCalculator.calculateFine(record.getDueDate(), record.getReturnDate());
            } else {
                // If not yet returned, calculate till today
                fine = FineCalculator.calculateFineTillToday(record.getDueDate());
            }
            // double fine = fineCalculator.calculateFineTillToday(record.getDueDate());
            System.out.println("Amount Due   : Rs. " +fine);
            System.out.println("----------------------------");
        }
    }
}
