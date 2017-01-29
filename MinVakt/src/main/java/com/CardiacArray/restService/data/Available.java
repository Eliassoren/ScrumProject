package com.CardiacArray.restService.data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by andreasbergman on 25/01/17.
 * Data class containing user objects that are available
 *
 */
public class Available extends User {

    private Date startTime;
    private Date endTime;

    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param mobile
     * @param email
     * @param password
     * @param admin
     * @param address
     * @param userCategoryInt
     * @param userCategoryString
     * @param token
     * @param expired
     * @param active
     * @param workPercent
     * @param departmentId
     * @param startTime
     * @param endTime
     */
    public Available(int id, String firstName, String lastName, int mobile, String email, String password, boolean admin, String address, int userCategoryInt, String userCategoryString, String token, Timestamp expired, boolean active, int workPercent,int departmentId,Date startTime, Date endTime){
        super(id,firstName,lastName,mobile,email,password,admin,address,userCategoryInt,userCategoryString,token,expired,active,workPercent, departmentId);
    }

    /**
     *
     * @return start time of available
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime start time of the available
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return end time of the available
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime end time of the available
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
