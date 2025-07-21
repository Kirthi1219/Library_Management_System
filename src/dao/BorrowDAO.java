package dao;

import model.BorrowRecord;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    // Count how many active books a user has borrowed (not returned yet)
    public int countActiveBorrowedBooks(int userId){
        String query="SELECT COUNT(*) FROM BorrowRecord WHERE user_id=? AND return_status=false";
        int count=0;

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs=stmt.executeQuery();

            if (rs.next()) {
                count=rs.getInt(1);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // INSERT borrow record (used by BorrowService)
    public boolean insertBorrowRecord(BorrowRecord record) {
        String query="INSERT INTO BorrowRecord (user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred) " +
                       "VALUES (?, ?, ?, ?, NULL, false, false, 0)";

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(query)) {

            stmt.setInt(1, record.getUserId());
            stmt.setInt(2, record.getBookId());
            stmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            stmt.setDate(4, Date.valueOf(record.getDueDate()));

            return stmt.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UPDATE borrow record (used for return or renew)
    public boolean updateBorrowRecord(BorrowRecord record) {
        String query="UPDATE BorrowRecord SET due_date =?, return_date=?, return_status=?, renewed=?, fine_incurred=? " +
                       "WHERE record_id =?";

        try (Connection conn =DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(record.getDueDate()));
            stmt.setDate(2, record.getReturnDate()!=null ? Date.valueOf(record.getReturnDate()) :null);
            stmt.setBoolean(3, record.getReturnStatus());
            stmt.setBoolean(4, record.isRenewed());
            stmt.setDouble(5, record.getFineIncurred());
            stmt.setInt(6, record.getRecordId());
            return stmt.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get active borrow record for a user and book
    public BorrowRecord getActiveBorrowRecord(int userId,int bookId) {
        String query ="SELECT * FROM BorrowRecord WHERE user_id =? AND book_id=? AND return_status = false";

        try (Connection conn=DBConnection.getConnection();
             PreparedStatement stmt= conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            ResultSet rs=stmt.executeQuery();

            if (rs.next()) {
                BorrowRecord record=new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());

                Date returnDate=rs.getDate("return_date");
                record.setReturnDate(returnDate != null ? returnDate.toLocalDate() : null);
                record.setReturnStatus(rs.getBoolean("return_status"));
                record.setRenewed(rs.getBoolean("renewed"));
                record.setFineIncurred(rs.getDouble("fine_incurred"));
                return record;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // List all currently borrowed books of a user (used in viewUserBorrowedBooks)
    public List<BorrowRecord> getBorrowedBooksByUser(int userId){
        List<BorrowRecord> list=new ArrayList<>();
        String query="SELECT * FROM BorrowRecord WHERE user_id = ? AND return_status = false";

        try (Connection conn= DBConnection.getConnection();
             PreparedStatement stmt=conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs =stmt.executeQuery();

            while (rs.next()) {
                BorrowRecord record=new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());

                Date returnDate=rs.getDate("return_date");
                record.setReturnDate(returnDate != null ? returnDate.toLocalDate() : null);

                record.setReturnStatus(rs.getBoolean("return_status"));
                record.setRenewed(rs.getBoolean("renewed"));
                record.setFineIncurred(rs.getDouble("fine_incurred"));
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
