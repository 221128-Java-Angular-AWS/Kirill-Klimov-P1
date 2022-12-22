package com.revature.service;
import com.revature.exceptions.*;
import com.revature.persistence.ReimbursementDao;
import com.revature.persistence.UserDao;
import com.revature.baseObjects.User;
import java.util.Set;
public class UserService {
    private UserDao userDao;
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public void registerNewUser(User user) {
        //logging, val.,
        userDao.createUser(user);
    }
    public void updateUser(User user){
        userDao.update(user);
    }

    public Set<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    public User getUserWithUsername(String username){
        return userDao.getUserWithUsername(username);
    }

    public User authenticateUser(String username, String password) throws UserNotFoundException, IncorrectPasswordException{
        return userDao.authenticate(username, password);
    }

    public void createUser(User user){
        userDao.createUser(user);
    }

    public boolean usernameExists(String username){
        return userDao.usernameExistsBoolean(username);
    }

}
