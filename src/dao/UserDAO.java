package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;
import model.User;

public class UserDAO {
    
    //Fetch a user by ID
    public User getUserById(int userId) {
        User user=null;
        String query="select * from User where user_id = ?";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(query)){
                ps.setInt(1, userId);
                try (ResultSet rs=ps.executeQuery()) {
                    if(rs.next()){
                    user=new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("ph_no")
                    );
                }
            }
        } catch (SQLException e){
            System.out.println("Error fetching user by ID");
            e.printStackTrace();
        }
        return user;
    }

    //Fetch all users
    public List<User> getAllUsers(){
        List<User> users=new ArrayList<>();
        String query="select * from User";

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement(query);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                User user=new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("ph_no")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users");
            e.printStackTrace();
        }
        return users;
    }

    //Add a new user
    public boolean addUser(User user) {
        String query="insert into User (name, ph_no) values (?, ?)";
        try (Connection conn=DBConnection.getConnection();
             PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getPhNo());
            int rowsAffected=ps.executeUpdate();
            return rowsAffected>0;
        } catch (SQLException e) {
            System.out.println("Error adding new user");
            e.printStackTrace();
        }
        return false;
    }
}
