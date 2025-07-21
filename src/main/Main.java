package main;
import model.*;
import dao.*;
import service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BookService bookService=new BookService();
        UserService userService=new UserService();
        BorrowService borrowService=new BorrowService(new BorrowDAO(),new BookDAO(),new FineCalculator());

        while (true) {
            System.out.println("\n=== Welcome to Mastech Library===");
            System.out.println("1. User");
            System.out.println("2. Librarian");
            System.out.println("3. Exit");
            System.out.print("Choose your role: ");
            int role = sc.nextInt();
            sc.nextLine();
            switch (role) {
                case 1:
                    userMenu(sc,bookService,userService,borrowService);
                    break;
                case 2:
                    librarianMenu(sc,bookService,userService);
                    break;
                case 3:
                    System.out.println("Thank you for visiting Mastech Library");
                    return;
                default:
                    System.out.println("Invalid!! Please Try again");
            }
        }
    }

    private static void userMenu(Scanner sc,BookService bookService,UserService userService,BorrowService borrowService){
        System.out.print("\nEnter your User ID (Enter 0 to register): ");
        int userId=sc.nextInt();
        sc.nextLine();

        if (userId==0){
            System.out.print("Enter your name: ");
            String name=sc.nextLine();
            System.out.print("Enter your phone number: ");
            String phNo=sc.nextLine();
            boolean success=userService.registerUser(name,phNo);
            if (success){
                System.out.println("You are registered successfully");
            } else {
                System.out.println("Registration failed! please try again");
            }
            return;
        }

        User user=userService.getUserById(userId);
        if (user==null) {
            System.out.println("User not found for this Id");
            return;
        }

        while (true){
            System.out.println("\nWelcome to Your Book Hub");
            System.out.println("1. View available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Renew a book");
            System.out.println("4. Return a book");
            System.out.println("5. View my borrowed books");
            System.out.println("6. Exit");
            System.out.print("Choose your next bookish move: ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bookService.displayBooks();
                    break;
                case 2:
                    System.out.print("Enter Book ID to borrow: ");
                    int borrowBookId=sc.nextInt();
                    sc.nextLine();
                    borrowService.borrowBook(userId,borrowBookId);
                    break;
                case 3:
                    System.out.print("Enter Book ID to renew: ");
                    int renewBookId=sc.nextInt();
                    sc.nextLine();
                    borrowService.renewBook(userId,renewBookId);
                    break;
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId =sc.nextInt();
                    sc.nextLine();
                    borrowService.returnBook(userId,returnBookId);
                    break;
                case 5:
                    borrowService.viewUserBorrowedBooks(userId);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void librarianMenu(Scanner sc,BookService bookService,UserService userService){
        while (true) {
            System.out.println("\nWelcome to Library Management Hub");
            System.out.println("1. Add new book");
            System.out.println("2. View all books");
            System.out.println("3. View available books");
            System.out.println("4. Check user status");
            System.out.println("5. View all users");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    Book book=new Book();
                    System.out.print("Enter book title: ");
                    book.setTitle(sc.nextLine());
                    System.out.print("Enter book author: ");
                    book.setAuthor(sc.nextLine());
                    System.out.print("Enter total number of copies: ");
                    book.setCopiesTotal(sc.nextInt());
                    sc.nextLine();
                    if (bookService.addBook(book)) {
                        System.out.println("Book added successfully");
                    } else {
                        System.out.println("Failed to add book");
                    }
                    break;
                case 2:
                    bookService.displayBooks();
                    break;
                case 3:
                    System.out.println("\nAvailable Books:");
                    for (Book b:bookService.getAvailableBooks()) {
                        System.out.println(b);
                        System.out.println("---------------------------");
                    }
                    break;
                case 4:
                    System.out.print("Enter user ID to check status: ");
                    int checkUserId=sc.nextInt();
                    sc.nextLine();
                    userService.checkUserStatus(checkUserId);
                    break;
                case 5:
                    userService.displayAllUsers();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
