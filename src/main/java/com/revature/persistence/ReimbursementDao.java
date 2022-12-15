package com.revature.persistence;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import com.revature.baseObjects.Reimbursement;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
public class ReimbursementDao {
    private Connection connection;
    //taskId, title, approved, description, user_id
    public ReimbursementDao(){
        this.connection = Connector.makeConnection();
    }

    public void create(Reimbursement reimbursement){
        String sql = "INSERT INTO reimbursements (title, approved, description, user_id) VALUES (?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, reimbursement.getTitle());
            pstmt.setString(2, reimbursement.getApproved().toString());
            pstmt.setString(3, reimbursement.getDescription());
            pstmt.setInt(4, reimbursement.getUserId());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                reimbursement.setTaskId(rs.getInt("task_id"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Reimbursement> getAllReimbursementRequests(){
        String sql = "SELECT * FROM reimbursements";
        Set<Reimbursement> reimbursements = new HashSet<>();
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Reimbursement reimbursement = new Reimbursement(rs.getInt("task_id"),
                        rs.getString("title"), rs.getBoolean("approved"), rs.getString("description"), rs.getInt("user_id"));
                reimbursements.add(reimbursement);
            }

        }catch (SQLException e){
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
