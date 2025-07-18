package model;

public class User {
    private int userId;
    private String name;
    private String phNo;

    public User() {}

    public User(int userId, String name, String phNo) {
        this.userId=userId;
        this.name=name;
        this.phNo=phNo;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setPhNo(String phNo) {
        this.phNo=phNo;
    }

    @Override
    public String toString() {
        return "User Details:\n" +
            "User ID   : " + userId + "\n" +
            "Name      : " + name + "\n" +
            "Phone No. : " + phNo;
    }
}
