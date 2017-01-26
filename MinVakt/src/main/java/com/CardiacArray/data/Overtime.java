package com.CardiacArray.data;


import java.sql.Time;

public class Overtime {
    private int overtimeId;
    private int shiftId;
    private Time startTime;
    private Time endTime;
    private int userId;
    private boolean approved;

    public Overtime(int overtimeId, int shiftId, Time startTime, Time endTime, int userId, boolean approved) {
        this.overtimeId = overtimeId;
        this.shiftId = shiftId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.approved = approved;
    }

    public int getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
