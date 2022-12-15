package com.revature.baseObjects;


import java.util.Objects;

public class Reimbursement {
    private Integer taskId;
    private String title;
    private Boolean approved;
    private String description;
    private Integer userId;

    public Reimbursement() {
    }

    public Reimbursement(Integer taskId, String title, Boolean approved, String description, Integer userId) {
        this.taskId = taskId;
        this.approved = approved;
        this.description = description;
        this.title = title;
        this.userId = userId;
    }

    public Reimbursement(String title, Boolean approved, String description,  Integer userId) {
        this.approved = approved;
        this.description = description;
        this.title = title;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(taskId, reimbursement.taskId) && Objects.equals(approved, reimbursement.approved) && Objects.equals(description, reimbursement.description) && Objects.equals(title, reimbursement.title) && Objects.equals(userId, reimbursement.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, approved, description, title, userId);
    }
}