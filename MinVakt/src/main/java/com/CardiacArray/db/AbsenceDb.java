package com.CardiacArray.db;

import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by andreasbergman on 24/01/17.
 */
public class AbsenceDb extends DbManager {

    private ResultSet res;
    private PreparedStatement statement;

    public AbsenceDb(){
        super();
    }


    public Shift getAbsence(User user) {
        int userId = user.getId();
        Shift shift = null;

        String toSql = "SELECT *  FROM absence WHERE user_id = ? ";
        try {
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, userId);
            res = statement.executeQuery();

            if(res.next()){
                Timestamp startTime = res.getTimestamp("start_time");
                Timestamp endTime = res.getTimestamp("end_time");
                int shiftId = res.getInt("shift_id");

                shift = new Shift(

                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shift;
    }
}