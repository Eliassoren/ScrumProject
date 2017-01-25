package com.CardiacArray.db;

import com.CardiacArray.data.Absence;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by andreasbergman on 24/01/17.
 */
public class AbsenceDb extends DbManager {

    private ResultSet res;
    private PreparedStatement statement;

    public AbsenceDb(){
        super();
    }


    public ArrayList<Absence> getAbsenceForUser(int userId, Timestamp sTime, Timestamp eTime) {
        ArrayList<Absence> absenceList = null;

        String toSql = "SELECT *  FROM absence WHERE user_id = ? ";
        try {
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, userId);
            res = statement.executeQuery();

            while(res.next()){
                Timestamp startTime = res.getTimestamp("start_time");
                Timestamp endTime = res.getTimestamp("end_time");
                int shiftId = res.getInt("shift_id");

                absenceList.add(new Absence(
                        startTime,
                        endTime,
                        userId
                ));
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }
        return absenceList;
    }

    public boolean setAbsence(int userId, Timestamp startTime, Timestamp endTime){
        boolean returnValue = false;
        String toSql = "INSERT INTO absence " +
                "(user_id, start_time, end_time,) " +
                "VALUES (?, ?, ?)";
        try{
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, userId);
            statement.setTimestamp(2, startTime);
            statement.setTimestamp(3, endTime);
            statement.execute();
            ResultSet res = statement.getGeneratedKeys();
            if(res.next()){
                returnValue = true;
            }
            res.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return returnValue;
    }
}