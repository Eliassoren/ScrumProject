package com.CardiacArray.data;

/**
 * Created by andreasbergman on 24/01/17.
 */
public class Changeover {

    private int oldUserId;
    private String oldUser;
    private int newUserId;
    private String newUser;
    private boolean approved;
    private int shiftId;
    private String fNameOld;
    private String lNameOld;
    private String fNameNew;
    private String lNameNew;

    public Changeover (){

    }

    public Changeover (User oldUser, User newUser, int shiftId){
        this.oldUserId = oldUser.getId();
        this.oldUser = oldUser.getEmail();
        this.fNameOld = oldUser.getFirstName();
        this.lNameOld = oldUser.getLastName();

        this.newUserId = newUser.getId();
        this.newUser = newUser.getEmail();
        this.fNameNew = newUser.getFirstName();
        this.lNameNew = newUser.getLastName();
    }

    public int getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(int oldUserId) {
        this.oldUserId = oldUserId;
    }

    public String getOldUser() {
        return oldUser;
    }

    public void setOldUser(String oldUser) {
        this.oldUser = oldUser;
    }

    public int getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(int newUserId) {
        this.newUserId = newUserId;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getfNameOld() {
        return fNameOld;
    }

    public void setfNameOld(String fNameOld) {
        this.fNameOld = fNameOld;
    }

    public String getlNameOld() {
        return lNameOld;
    }

    public void setlNameOld(String lNameOld) {
        this.lNameOld = lNameOld;
    }

    public String getfNameNew() {
        return fNameNew;
    }

    public void setfNameNew(String fNameNew) {
        this.fNameNew = fNameNew;
    }

    public String getlNameNew() {
        return lNameNew;
    }

    public void setlNameNew(String lNameNew) {
        this.lNameNew = lNameNew;
    }

}
