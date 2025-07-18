package model;

public class Librarian {
    private int librarianId;
    private String name;
    private String username;
    private String password;

    //default constructor
    public Librarian() {}

    //parameterised constructor
    public Librarian(int librarianId, String name, String username, String password) {
        this.librarianId=librarianId;
        this.name=name;
        this.username=username;
        this.password=password;
    }

    // Getters and Setters
    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId=librarianId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

   @Override
    public String toString() {
        return "Librarian Details:\n" +
            "Librarian ID : " + librarianId + "\n" +
            "Name         : " + name + "\n" +
            "Username     : " + username + "\n" +
            "Password     : " + password;
    }
}
