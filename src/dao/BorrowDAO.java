package dao;

import model.BorrowRecord;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    // Count how many active books a user has borrowed (not returned yet)
    public int countActiveBorrowedBooks(int userId) {
        String query = "SELECT COUNT(*) FROM BorrowRecord WHERE user_id = ? AND return_status = false";
        int count = 0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Borrow a book
    public boolean borrowBook(BorrowRecord record) {
        String query = "INSERT INTO BorrowRecord (user_id, book_id, borrow_date, due_date, return_date, return_status, renewed, fine_incurred) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, record.getUserId());
            stmt.setInt(2, record.getBookId());
            stmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            stmt.setDate(4, Date.valueOf(record.getDueDate()));
            stmt.setDate(5, null); // Initially return_date is null
            stmt.setBoolean(6, false);
            stmt.setBoolean(7, false);
            stmt.setDouble(8, 0);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Return a book
    public boolean returnBook(int recordId, LocalDate returnDate, double fine) {
        String query = "UPDATE BorrowRecord SET return_date = ?, return_status = true, fine_incurred = ? WHERE record_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setDouble(2, fine);
            stmt.setInt(3, recordId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Renew a book (can be done only once)
    public boolean renewBook(int recordId, LocalDate newDueDate) {
        String query = "UPDATE BorrowRecord SET due_date = ?, renewed = true WHERE record_id = ? AND renewed = false";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(newDueDate));
            stmt.setInt(2, recordId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get BorrowRecord by ID
    public BorrowRecord getBorrowRecordById(int recordId) {
        String query = "SELECT * FROM BorrowRecord WHERE record_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, recordId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returnDate = rs.getDate("return_date");
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

    // List all borrow records
    public List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> list = new ArrayList<>();
        String query = "SELECT * FROM BorrowRecord";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returnDate = rs.getDate("return_date");
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

    // Get active borrow records by user
    public List<BorrowRecord> getActiveBorrowsByUser(int userId) {
        List<BorrowRecord> list = new ArrayList<>();
        String query = "SELECT * FROM BorrowRecord WHERE user_id = ? AND return_status = false";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returnDate = rs.getDate("return_date");
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
