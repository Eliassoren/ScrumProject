package com.CardiacArray.restService.data;


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

    /**
     *
     * @return overtime id as int
     */
    public int getOvertimeId() {
        return overtimeId;
    }

    /**
     *
     * @param overtimeId id for overtime as int
     */
    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }

    /**
     *
     * @return id for shift as int
     */
    public int getShiftId() {
        return shiftId;
    }

    /**
     *
     * @param shiftId shift id as int
     */
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    /**
     *
     * @return start time for the shift as a Date
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime start time for a shift as a Date
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @param startTime start time for a shift as a string
     */
    public void setStartTime(String startTime) {
        this.startTime = new Date(Long.parseLong(startTime));
    }

    /**
     *
     * @return end time for a shift as a Date
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime end time for a shift as a Date
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @param endTime end time for a shift as a String
     */
    public void setEndTime(String endTime) {
        this.endTime = new Date(Long.parseLong(endTime));
    }

    /**
     *
     * @return id for a shift
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId id for a shift as int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return bool if shift is approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     *
     * @param approved bool for if shift is approved or not
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     *
     * @return first name for user on shift as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName first name for user on shift
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return last name for user on shift
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName last name for user on shift
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
