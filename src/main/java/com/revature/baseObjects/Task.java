package com.revature.baseObjects;


import java.util.HashMap;
import java.util.Objects;

public class Task {
    private Integer taskId;
    private Boolean completed;
    private String description;
    private String title;

    public Task() {}

    public Task( Boolean completed, String description, String title){
        this.completed = completed;
        this.description = description;
        this.title = title;
    }

    public Task(Integer taskId, Boolean completed, String description, String title) {
        this.taskId = taskId;
        this.completed = completed;
        this.description = description;
        this.title = title;
    }

    public Integer getTaskId(){
        return this.taskId;
    }

    public void setTaskId(Integer taskId){
        this.taskId = taskId;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed){
        this.completed = completed;
    }

    public Boolean getCompleted(){
        return this.completed;
    }
    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if(o==null || this.getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(task.completed, this.completed) && Objects.equals(this.taskId, task.taskId) && Objects.equals(description, task.description) && Objects.equals(title, task.title);

    }

    @Override
    public int hashCode(){
        return Objects.hash(taskId, completed, description, title);
    }
}