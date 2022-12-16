package com.revature.service;
import com.revature.baseObjects.Reimbursement;
import com.revature.baseObjects.User;
import java.util.Set;
import com.revature.persistence.ReimbursementDao;

public class ReimbursementService {
    private ReimbursementDao reimbursementDao;

    public ReimbursementService(ReimbursementDao reimbursementDao){ this.reimbursementDao = reimbursementDao;}

    public void createReimbursement(Reimbursement reimbursement){
        reimbursementDao.create(reimbursement);
    }
    public Set<Reimbursement> getAllReimbursements(){
        return reimbursementDao.getAllReimbursementRequests();
    }

    public Set<Reimbursement> getAllReimbursementsForAUser(User user){
        return reimbursementDao.getAllReimbursementsForAUser(user.getUserId());
    }

    public Set<Reimbursement> getReimbursementsFilterByApproval(Reimbursement reimbursement){
        return reimbursementDao.filterByApproval(reimbursement.getApproved());
    }

}
