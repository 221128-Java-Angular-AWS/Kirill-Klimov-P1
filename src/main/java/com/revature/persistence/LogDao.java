package com.revature.persistence;
import com.revature.baseObjects.Log;

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
}
