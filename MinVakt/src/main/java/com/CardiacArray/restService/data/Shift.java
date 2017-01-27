package com.CardiacArray.restService.data;

import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;

public class Shift {
    private int shiftId;
    private Date startTime;
    private Date endTime;
    private int userId;
    private String userName;
    private int departmentId;
    private int role;
    private String roleDescription;
    private boolean tradeable;
    private boolean responsibleUser;
    private Time startTimeTime;
    private Time endTimeTime;

    public Shift(int shiftId, Date startTime, Date endTime, int userId, String userName, int departmentId, int role, boolean tradeable, boolean responsibleUser, String roleDescription) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
        if (startTime.getTime() >= endTime.getTime()) throw new IllegalArgumentException("Shift duration is less than 1 min");
        this.shiftId = shiftId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.userName = userName;
        this.departmentId = departmentId;
        this.role = role;
        this.tradeable = tradeable;
        this.responsibleUser = responsibleUser;
        this.roleDescription = roleDescription;
    }

    public Shift(Date startTime, Date endTime, int departmentId, int role, boolean responsibleUser, String roleDescription) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
        if (startTime.getTime() >= endTime.getTime()) throw new IllegalArgumentException("Shift duration is less than 1 min");
        this.startTime = startTime;
        this.endTime = endTime;
        this.departmentId = departmentId;
        this.role = role;
        this.responsibleUser = responsibleUser;
        this.roleDescription = roleDescription;
    }

    public Shift(){
    }

    /**
     *
     * @return the id of a shift as int
     */
    public int getShiftId() {
        return shiftId;
    }

    /**
     * Sets the id of the shift
     * @param shiftId a new shift id as int
     */
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    /**
     *
     * @return start time of the shift as Date
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the shift
     * @param startTime a new start time as String
     */
    public void setStartTime(String startTime) {
        this.startTime = new Date(Long.parseLong(startTime));
    }

    /**
     *
     * @return end time of the shift as Date
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the shift
     * @param endTime a new end time as String
     */
    public void setEndTime(String endTime) {
        this.endTime = new Date(Long.parseLong(endTime));
    }

    /**
     *
     * @return id of the user as int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user
     * @param userId a new id as int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return user name of the user as String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name of the user
     * @param userName a new user name as String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return department id of the user as int
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department id of the user
     * @param departmentId a new id as int
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     *
     * @return role of the user as int
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets the role of the user
     * @param role a new role as int
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     *
     * @return true if the shift is tradeable, false if not
     */
    public boolean isTradeable() {
        return tradeable;
    }

    /**
     * Sets true if shift is tradeable, false if not
     * @param tradeable true or false
     */
    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }

    /**
     *
     * @return true if user responsible of the shift, false if not
     */
    public boolean isResponsibleUser() {
        return responsibleUser;
    }

    /**
     * Sets true if the user is responsible of the shift, false if not
     * @param responsibleUser true of false
     */
    public void setResponsibleUser(boolean responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    /**
     *
     * @return Role description of the user as String
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * Sets the role description of the user
     * @param roleDescription a new role description as String
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     *
     * @return start time of the shift as Time
     */
    public Time getStartTimeTime() {
        return startTimeTime;
    }

    /**
     * Sets the start time of the shift
     * @param startTimeTime a new start time as Time
     */
    public void setStartTimeTime(Time startTimeTime) {
        this.startTimeTime = startTimeTime;
    }

    /**
     *
     * @return end time of the shift as Time
     */
    public Time getEndTimeTime() {
        return endTimeTime;
    }

    /**
     * Sets the end time of the shift
     * @param endTimeTime a new end time as Time
     */
    public void setEndTimeTime(Time endTimeTime) {
        this.endTimeTime = endTimeTime;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "shiftId=" + shiftId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", departmentId=" + departmentId +
                ", role=" + role +
                ", roleDescription='" + roleDescription + '\'' +
                ", tradeable=" + tradeable +
                ", responsibleUser=" + responsibleUser +
                '}';
    }
}
