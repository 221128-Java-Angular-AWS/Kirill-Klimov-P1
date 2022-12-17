package com.revature.baseObjects;


import java.util.Objects;

public class Reimbursement {
    private Integer taskId;
    private String title;
    private Boolean approved = false;
    private String description;
    private String username;

    public Reimbursement() {
    }

    public Reimbursement(Integer taskId, String title, String description, String username) {
        this.taskId = taskId;
        //this.approved = approved;
        this.description = description;
        this.title = title;
        this.username = username;
    }

    public Reimbursement(String title, String description,  String username) {
        //this.approved = approved;
        this.description = description;
        this.title = title;
        this.username = username;
        //this.userId = userId;
    }

    public Reimbursement(Boolean approved){
        this.approved = approved;
    }
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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

    public String getUsername() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(taskId, reimbursement.taskId) && Objects.equals(approved, reimbursement.approved) && Objects.equals(description, reimbursement.description) && Objects.equals(title, reimbursement.title) && Objects.equals(username, reimbursement.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, approved, description, title, username);
    }
}