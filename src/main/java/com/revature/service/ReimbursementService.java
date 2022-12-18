package com.revature.service;
import com.revature.baseObjects.Reimbursement;
import com.revature.persistence.UserDao;

import java.util.ArrayDeque;
import java.util.Set;
import com.revature.persistence.ReimbursementDao;

public class ReimbursementService {
    private ReimbursementDao reimbursementDao;
    private UserDao userDao;

    public ReimbursementService(ReimbursementDao reimbursementDao){ this.reimbursementDao = reimbursementDao;}

    public void createReimbursement(Reimbursement reimbursement){
        //System.out.println("*"+ username);
        reimbursementDao.create(reimbursement);
    }
    public Set<Reimbursement> getAllReimbursements(){
        return reimbursementDao.getAllReimbursementRequests();
    }

    public Set<Reimbursement> getAllReimbursementsForAUser(String username){
        return reimbursementDao.getAllReimbursementsForAUser(username);
    }
    public void addLastInputReimbursement(){
        reimbursementDao.queueRecentlyInputReimbursement();
    }
    public ArrayDeque<Reimbursement> getQueueOfReimbursements(){
        return reimbursementDao.getReimbursementQueue();
    }
    public Set<Reimbursement> getAllReimbursementsFilterByApproval(String type){
        return reimbursementDao.filterByApproval(type);
    }

}
