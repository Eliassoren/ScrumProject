package com.CardiacArray.data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by andreasbergman on 25/01/17.
 */
public class Available extends User {

    private Date startTime;
    private Date endTime;

    public Available(int id, String firstName, String lastName, int mobile, String email, String password, boolean admin, String address, int userCategoryInt, String userCategoryString, String token, Timestamp expired, boolean active, int workPercent,int departmentId,Date startTime, Date endTime){
        super(id,firstName,lastName,mobile,email,password,admin,address,userCategoryInt,userCategoryString,token,expired,active,workPercent, departmentId);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
