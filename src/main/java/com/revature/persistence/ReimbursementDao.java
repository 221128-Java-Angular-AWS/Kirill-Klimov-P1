package com.revature.persistence;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import com.revature.baseObjects.*;
import java.util.ArrayDeque;

public class ReimbursementDao {
    private ArrayDeque<Reimbursement> reimbursementQueue;
    private Connection connection;
    //taskId, title, approved, description, user_id
    public ReimbursementDao(){
        this.reimbursementQueue = new ArrayDeque<>();
        this.connection = Connector.makeConnection();
    }

    public void create(Reimbursement reimbursement){
        //UserDao userDao = new UserDao();
        //User user = userDao.getUserWithUsername(username);
        //Reimbursement reimbursement = new Reimbursement(title, description, username);
        String sql = "BEGIN; INSERT INTO reimbursements (amount, description, username) VALUES (?,?,?); COMMIT TRANSACTION;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, reimbursement.getAmount());
            //pstmt.setString(2, reimbursement.getApproved().toString());
            pstmt.setString(2, reimbursement.getDescription());
            pstmt.setString(3, reimbursement.getUsername());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                reimbursement.setTicketId(rs.getInt("reimbursement_id"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void queueRecentlyInputReimbursement(){
        String sql = "SELECT * FROM reimbursements ORDER BY reimbursement_id DESC LIMIT 1;";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Reimbursement reimbursement = new Reimbursement(rs.getInt("reimbursement_id"),
                        rs.getDouble("amount"), rs.getString("approved"),  rs.getString("description"), rs.getString("username"));
                reimbursementQueue.add(reimbursement);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public ArrayDeque<Reimbursement> getReimbursementQueue(){
        return reimbursementQueue;
    }

    public Set<Reimbursement> getAllReimbursementRequests(){
        String sql = "SELECT * FROM reimbursements";
        Set<Reimbursement> reimbursements = new HashSet<>();
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Reimbursement reimbursement = new Reimbursement(rs.getInt("reimbursement_id"),
                        rs.getDouble("amount"), rs.getString("approved"),  rs.getString("description"), rs.getString("username"));
                reimbursements.add(reimbursement);
            }

        }catch (SQLException e){
            throw new RuntimeException();
        }
        return reimbursements;
    }

    public Set<Reimbursement> getAllReimbursementsForAUser(String username){
        Set<Reimbursement> reimbursements= new HashSet<>();
        String sql = "SELECT * FROM reimbursements WHERE username = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Reimbursement reimbursement = new Reimbursement(rs.getInt("reimbursement_id"), rs.getDouble("amount"),
                         rs.getString("approved"), rs.getString("description"), rs.getString("username"));
                reimbursements.add(reimbursement);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reimbursements;
    }
    public Set<Reimbursement> filterByApproval(String approvedType){
        Set<Reimbursement> reimbursements = new HashSet<>();
        String sql = "SELECT * FROM reimbursements " +
                "WHERE approved = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, approvedType);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Reimbursement reimbursement = new Reimbursement(
                        rs.getInt("reimbursement_id"),
                        rs.getDouble("amount"),
                        rs.getString("approved"),
                        rs.getString("description"),
                        rs.getString("username"));
                reimbursements.add(reimbursement);
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return reimbursements;
    }

    public void alterApproval(Reimbursement reimbursement){
        //search for all reimbursements where task_id = x
        String sql = "UPDATE reimbursements SET approved = ? WHERE task_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, reimbursement.getApproved().toString());

        } catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
