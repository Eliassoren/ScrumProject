package com.CardiacArray.restService.data;

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

    /**
     *
     * @return the start time of absence in timestamp
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return the end time of absence in timestamp
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime time of the absence
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId a new id as int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "Fravær: Fra " + startTime + " Til " + endTime;
    }
}
