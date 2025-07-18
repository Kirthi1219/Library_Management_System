package util;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn=DBConnection.getConnection();

        if (conn!=null) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed.");
        }
    }
}
