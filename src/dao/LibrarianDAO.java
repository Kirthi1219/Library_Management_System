package dao;

import model.Librarian;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianDAO {

    // Authenticate login
    public Librarian authenticate(String username, String password) {
        Librarian librarian=null;

        String sql="select * from librarian where username= ? AND password =?";

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs=stmt.executeQuery()) {
                if (rs.next()) {
                    librarian=new Librarian();
                    librarian.setLibrarianId(rs.getInt("librarian_id"));
                    librarian.setName(rs.getString("name"));
                    librarian.setUsername(rs.getString("username"));
                    librarian.setPassword(rs.getString("password")); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarian;
    }

    //Add new librarian
    public boolean addLibrarian(Librarian librarian) {
        String sql="insert into librarian (name, username, password) VALUES (?, ?, ?)";

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(sql)) {

            stmt.setString(1, librarian.getName());
            stmt.setString(2, librarian.getUsername());
            stmt.setString(3, librarian.getPassword());

            int rows=stmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
