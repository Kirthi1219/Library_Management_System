package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "src/db.properties";

    public static Connection getConnection() {
        Connection conn=null;

        try {
            Properties props=new Properties();
            FileInputStream fis=new FileInputStream(PROPERTIES_FILE);
            props.load(fis);

            String url=props.getProperty("db.url");
            String username=props.getProperty("db.username");
            String password=props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            System.out.println("Could not load db.properties");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }

        return conn;
    }
}
