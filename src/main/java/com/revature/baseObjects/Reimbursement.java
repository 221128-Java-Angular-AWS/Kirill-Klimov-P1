package com.revature.baseObjects;


import java.util.Objects;

public class Reimbursement {
    private Integer ticketId;
    private Double amount;
    private String approved = "Pending";
    private String description;
    private String username;

    public Reimbursement() {
    }

    public Reimbursement(Integer ticketId, Double amount, String description, String username) {
        this.ticketId = ticketId;
        //this.approved = approved;
        this.description = description;
        this.amount = amount;
        this.username = username;
    }

    public Reimbursement(Double amount, String description, String username) {
        //this.approved = approved;
        this.description = description;
        this.amount = amount;
        this.username = username;
        //this.userId = userId;
    }

    /*public Reimbursement(String approved){
        this.approved = approved;
    }*/
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        return Objects.equals(ticketId, reimbursement.ticketId) && Objects.equals(approved, reimbursement.approved) && Objects.equals(description, reimbursement.description) && Objects.equals(amount, reimbursement.amount) && Objects.equals(username, reimbursement.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, approved, description, amount, username);
    }
}