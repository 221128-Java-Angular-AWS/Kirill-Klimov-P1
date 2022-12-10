package com.revature.baseObjects;


import java.util.HashMap;
import java.util.Objects;

public class Task {
    private Integer taskId;
    private Boolean completed;
    private String description;
    private String title;
    private Integer user_id;

    public Task() {
    }

    public Task(Integer taskId, Boolean completed, String description, String title, Integer user_id) {
        this.taskId = taskId;
        this.completed = completed;
        this.description = description;
        this.title = title;
        this.user_id = user_id;
    }

    public Task(Boolean completed, String description, String title, Integer user_id) {
        this.completed = completed;
        this.description = description;
        this.title = title;
        this.user_id = user_id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /*@Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || o.getClass() != this.getClass()) return false;
        Task task = (Task) o;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId) && Objects.equals(completed, task.completed) && Objects.equals(description, task.description) && Objects.equals(title, task.title) && Objects.equals(user_id, task.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, completed, description, title, user_id);
    }
}