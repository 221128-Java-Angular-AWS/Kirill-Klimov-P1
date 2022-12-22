package com.revature.persistence;
import com.revature.baseObjects.Log;
import com.revature.baseObjects.Reimbursement;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.sql.*;

public class LogDao {
    private Connection connection;

    public LogDao(){
        this.connection = Connector.makeConnection();
    }

    public void create(Log log){
        //UserDao userDao = new UserDao();
        //User user = userDao.getUserWithUsername(username);
        //Reimbursement reimbursement = new Reimbursement(title, description, username);
        String sql = "BEGIN; INSERT INTO logs (date_time, username, event) VALUES (?,?,?); COMMIT TRANSACTION;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setTimestamp(1, log.getDateTime());
            pstmt.setString(2, log.getUsername());
            pstmt.setString(3, log.getEvent());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                log.setLogId(rs.getInt("log_id"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Log> getMyLogs(String username){
        String sql = "SELECT * FROM logs WHERE username = ?;";
        Set<Log> logSet = new LinkedHashSet<>();
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Log log = new Log( rs.getInt("log_id"), rs.getTimestamp("date_time"), rs.getString("username"), rs.getString("event"));
                logSet.add(log);
            }
            return logSet;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
