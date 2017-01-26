package com.CardiacArray.data;


import java.util.Date;

public class Overtime {
    private int overtimeId;
    private int shiftId;
    private Date startTime;
    private Date endTime;
    private int userId;
    private String firstName;
    private String lastName;
    private boolean approved;

    public Overtime(int overtimeId, int shiftId, int userId, String firstName, String lastName, Date startTime, Date endTime, boolean approved) {
        this.overtimeId = overtimeId;
        this.shiftId = shiftId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.approved = approved;
    }

    public Overtime() {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = new Date(Long.parseLong(startTime));
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = new Date(Long.parseLong(endTime));
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
