package com.revature.service;
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
        userDao.create(user);
    }

    public Set<User> getAllUsers(){
        return userDao.getAllUsers();
    }
}
