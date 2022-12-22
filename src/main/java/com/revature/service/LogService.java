package com.revature.service;
import com.revature.baseObjects.*;
import com.revature.persistence.LogDao;
import  java.util.Set;
public class LogService {
    private LogDao logDao;

    public LogService( LogDao logDao){
        this.logDao = logDao;
    }

    public Set<Log> getMyLogs(String username){
        return logDao.getMyLogs(username);
    }

    public void addLog(Log log){
        logDao.create(log);
    }
}
