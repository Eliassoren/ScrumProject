package com.CardiacArray.data;

import java.sql.Timestamp;

/**
 * Created by andreasbergman on 24/01/17.
 */
public class Absence {
    private Timestamp startTime;
    private Timestamp endTime;
    private int userId;

    public Absence(Timestamp startTime, Timestamp endTime, int userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "Fravær: Fra " + startTime + " Til " + endTime;
    }
}
