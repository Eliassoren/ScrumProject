package com.CardiacArray.data;

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

    public Shift(int shiftId, Date startTime, Date endTime, int userId, String userName, int departmentId, int role, boolean tradeable, boolean responsibleUser) {
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
    }

    public Shift(Date startTime, Date endTime, int departmentId, int role, boolean responsibleUser) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
        if (startTime.getTime() >= endTime.getTime()) throw new IllegalArgumentException("Shift duration is less than 1 min");
        this.startTime = startTime;
        this.endTime = endTime;
        this.departmentId = departmentId;
        this.role = role;
        this.responsibleUser = responsibleUser;
    }

    public Shift(){
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

    public void setStartTime(String startTime) {
        this.startTime = new Date(Long.parseLong(startTime));
    }

    public Date getEndTime() {
        return endTime;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }

    public boolean isResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(boolean responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
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
