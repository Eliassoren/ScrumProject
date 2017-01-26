package com.CardiacArray.db;

import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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


    /**
     *
     * @param user
     * @return All shifts where a user has been absent from a shift.
     */
    public ArrayList<Shift> getAbsence(User user) {
        ArrayList<Shift> al = new ArrayList<>();
        int userId = user.getId();

        String toSql = "SELECT *  FROM absence WHERE user_id = ? ";
        try {
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, userId);
            res = statement.executeQuery();

            while(res.next()){
                Timestamp startTime = res.getTimestamp("start_time");
                Timestamp endTime = res.getTimestamp("end_time");
                int shiftId = res.getInt("shift_id");

                Shift shift = new Shift();
                shift.setStartTime(String.valueOf(startTime.getTime()));
                shift.setStartTime(String.valueOf(endTime.getTime()));
                shift.setShiftId(shiftId);

                al.add(shift);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }
}