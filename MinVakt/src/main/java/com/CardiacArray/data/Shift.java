package com.CardiacArray.data;

import java.util.Date;

public class Shift {

    private int time;
    private Date date;
    private int departmentId;
    private String role;
    private int shiftId;
    private boolean tradeable;
    private boolean shiftManager;
    
    
    public Shift(int time, Date date, int departmentId, String role, int shiftId, boolean tradeable, boolean shiftManager) {
        this.time = time;
        this.date = date;
        this.departmentId = departmentId;
        this.role = role;
        this.shiftId = shiftId;
        this.tradeable = tradeable;
        this.shiftManager = shiftManager;
    }
    
    public int getTime() {
        return time;
    }
    
    public void setTime(int time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public boolean getTradeable() {
        return tradeable;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }

    public boolean getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(boolean shiftManager) {
        this.shiftManager = shiftManager;
    }
    
    
    
    
    public static void main(String[] args) {
        
    }
    
}
