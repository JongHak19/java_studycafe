package studycafe.db;

import studycafe.model.Member;
import studycafe.model.Seat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/studycafe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // Member related methods
    public Member findMemberByIdAndPassword(String id, String password) {
        String sql = "SELECT * FROM member WHERE ID = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean createMember(Member member) {
        String sql = "INSERT INTO member (ID, password, name, phone_number, time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getPhoneNumber());
            pstmt.setInt(5, member.getTime());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Member> findAllMembers() {
        String sql = "SELECT * FROM member";
        List<Member> members = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    public boolean updateMemberTime(String id, int time) {
        String sql = "UPDATE member SET time = ? WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, time);
            pstmt.setString(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean decrementMemberTime(String id) {
        String sql = "UPDATE member SET time = SUBTIME(time, 1) WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Seat related methods
    public List<Seat> findAllSeats() {
        String sql = "SELECT * FROM seat ORDER BY seat_number";
        List<Seat> seats = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                seats.add(mapResultSetToSeat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }
    
    public boolean updateSeatStatus(int seatNumber, boolean isOccupied, String userId) {
        String sql = "UPDATE seat SET is_occupied = ?, user_id = ? WHERE seat_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, isOccupied);
            pstmt.setString(2, userId);
            pstmt.setInt(3, seatNumber);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean setMemberSeat(String memberId, int seatNumber) {
        String sql = "UPDATE member SET seat_number = ?, is_using = true, buy_time = NOW() WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seatNumber);
            pstmt.setString(2, memberId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean checkoutMember(String memberId) {
        String sql = "UPDATE member SET seat_number = NULL, is_using = false, buy_time = NULL WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, memberId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Checkout expired users
    public void checkoutExpiredUsers() {
        String sql = "UPDATE member SET seat_number = NULL, is_using = false, buy_time = NULL WHERE time <= 0 AND is_using = true";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Decrement all active user times
    public void decrementAllActiveMemberTimes() {
        String sql = "UPDATE member SET time = SUBTIME(time, 1) WHERE is_using = true AND time > 0";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getString("ID"));
        member.setPassword(rs.getString("password"));
        member.setName(rs.getString("name"));
        member.setPhoneNumber(rs.getString("phone_number"));
        member.setTime(rs.getInt("time"));
        member.setSeatNumber(rs.getInt("seat_number"));
        member.setUsing(rs.getBoolean("is_using"));
        
        Timestamp buyTime = rs.getTimestamp("buy_time");
        if (buyTime != null) {
            member.setBuyTime(buyTime.toLocalDateTime());
        }
        
        return member;
    }
    
    private Seat mapResultSetToSeat(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatNumber(rs.getInt("seat_number"));
        seat.setOccupied(rs.getBoolean("is_occupied"));
        seat.setUserId(rs.getString("user_id"));
        seat.setRemainingTime(rs.getInt("remaining_time"));
        return seat;
    }
}
