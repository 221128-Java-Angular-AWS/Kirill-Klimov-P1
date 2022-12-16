package com.revature.persistence;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import com.revature.baseObjects.User;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;

public class UserDao {
    private Connection connection;

    public UserDao(){
        this.connection = Connector.makeConnection();
    }

    public void create(User user){
        String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                user.setUserId(rs.getInt("user_id"));
                System.out.println("Debuggin: "+ user.getUserId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User authenticate(String username, String password) throws UserNotFoundException, IncorrectPasswordException{
        String sql = "SELECT * FROM users WHERE username = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                throw new UserNotFoundException("This username does not exist in our database");
            }
            User user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"),
                    rs.getString("password"), rs.getString("title"));
            if (password.equals(user.getPassword())){
                return user;
            }
            throw new IncorrectPasswordException("The password you have entered is incorrect.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void update(User user){
        String sql = "UPDATE USERS SET first_name = ?, last_name = ?, username = ?, password = ? WHERE user_id = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getUserId());
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public Set<User> getAllUsers(){
        String sql = "SELECT * FROM users";
        Set<User> setUsers = new HashSet<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                User user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"),
                        rs.getString("password"), rs.getString("title"));
                setUsers.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return setUsers;
    }

    public User authenticate(String username, String password) throws UserNotFoundException, IncorrectPasswordException {
        try{
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()){
                throw new UserNotFoundException("This username does not exist in our database");
            }

            User user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("username"), rs.getString("password"), rs.getString("title"));

            if(user.getPassword().equals(password)) {
                return user;
            }

            // password incoirrext excenptionthrow new
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void deleteUser(User user){
        String sql = "DELETE FROM users WHERE USER_ID = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
