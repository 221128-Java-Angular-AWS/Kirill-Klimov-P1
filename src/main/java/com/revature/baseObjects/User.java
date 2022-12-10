package com.revature.baseObjects;


import java.util.HashMap;
import java.util.Objects;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String title;
    private int taskId;

    //private String time_created;
    //no args: constructor:
    public User(){
    }

    public User(int userId, String firstName, String lastName, String password, String username, String title, int taskId){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.title = title;
        this.taskId = taskId;
    }

    public User(String firstName, String lastName, String password, String username, String title, int taskId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.title = title;
        this.taskId = taskId;
    }

    public int getUserId(){
        return this.userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }


    public int getTaskId(){
        return this.taskId;
    }
    public void setTaskId(int taskId){
        this.taskId = taskId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if (o==null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && taskId == user.taskId && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(username, user.username) && Objects.equals(title, user.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, password, username, title, taskId);
    }

}