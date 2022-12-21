package com.revature.service;
import com.revature.baseObjects.*;
import com.revature.persistence.LogDao;
public class LogService {
    private LogDao logDao;

    public LogService( LogDao logDao){
        this.logDao = logDao;
    }

    public void addLog(Log log){
        logDao.create(log);
    }
}
