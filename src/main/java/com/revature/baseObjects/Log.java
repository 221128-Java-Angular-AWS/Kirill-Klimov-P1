package com.revature.baseObjects;
import java.sql.Timestamp;
import java.util.Objects;

public class Log {

    private Integer logId;
    private String username;
    private String  event;
    private Timestamp dateTime;

    public Log() {};

    public Log(Integer logId, Timestamp dateTime, String username,String event){
        this.logId = logId;
        this.username = username;
        this.event = event;
        this.dateTime = dateTime;
    }
    public Log(String username, String event){
        //this.logId = logId;
        this.username = username;
        this.event = event;

        long now = System.currentTimeMillis();
        Timestamp timeStamp = new Timestamp(now);
        this.dateTime =timeStamp;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(logId, log.logId) && Objects.equals(username, log.username) && Objects.equals(event, log.event) && Objects.equals(dateTime, log.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, username, event, dateTime);
    }
}
