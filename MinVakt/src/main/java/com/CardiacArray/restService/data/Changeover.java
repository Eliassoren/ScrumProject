package com.CardiacArray.restService.data;

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

    /**
     *
     * @return old user id as int
     */
    public int getOldUserId() {
        return oldUserId;
    }

    /**
     *
     * @param oldUserId a old user id to be set as int
     */
    public void setOldUserId(int oldUserId) {
        this.oldUserId = oldUserId;
    }

    /**
     *
     * @return old user as a String
     */
    public String getOldUser() {
        return oldUser;
    }

    /**
     *
     * @param oldUser a old user to be set as String
     */
    public void setOldUser(String oldUser) {
        this.oldUser = oldUser;
    }

    /**
     *
     * @return a new user id as int
     */
    public int getNewUserId() {
        return newUserId;
    }

    /**
     *
     * @param newUserId a new user id to be set as int
     */
    public void setNewUserId(int newUserId) {
        this.newUserId = newUserId;
    }

    /**
     *
     * @return a new user as String
     */
    public String getNewUser() {
        return newUser;
    }

    /**
     *
     * @param newUser a new user to be set as String
     */
    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    /**
     *
     * @return true if approved, false if not
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     *
     * @param approved set approved as true or false
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     *
     * @return the id of a shift as int
     */
    public int getShiftId() {
        return shiftId;
    }

    /**
     *
     * @param shiftId a new shift id to be set as int
     */
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    /**
     *
     * @return a old first name as String
     */
    public String getfNameOld() {
        return fNameOld;
    }

    /**
     *
     * @param fNameOld a old first name to be set as String
     */
    public void setfNameOld(String fNameOld) {
        this.fNameOld = fNameOld;
    }

    /**
     *
     * @return a old last name as String
     */
    public String getlNameOld() {
        return lNameOld;
    }

    /**
     *
     * @param lNameOld a old last name to be set as String
     */
    public void setlNameOld(String lNameOld) {
        this.lNameOld = lNameOld;
    }

    /**
     *
     * @return a new first name as String
     */
    public String getfNameNew() {
        return fNameNew;
    }

    /**
     *
     * @param fNameNew a new first name to be set as String
     */
    public void setfNameNew(String fNameNew) {
        this.fNameNew = fNameNew;
    }

    /**
     *
     * @return a new last name as String
     */
    public String getlNameNew() {
        return lNameNew;
    }

    /**
     *
     * @param lNameNew a new last name to be set as String
     */
    public void setlNameNew(String lNameNew) {
        this.lNameNew = lNameNew;
    }

}
