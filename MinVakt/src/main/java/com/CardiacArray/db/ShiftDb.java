package com.CardiacArray.db;

import com.CardiacArray.data.Shift;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vegard on 12/01/2017.
 */
public class ShiftDb {
    private Connection connection;
    private ResultSet res;
    private PreparedStatement statement;

    public ShiftDb(Connection connection){
        this.connection = connection;
    }

    /*
    * Returns a single shift object
    *
    * @author Vegard Stenvik
    * @param date the date for the shift
    * @param userId the id that identifies the user
    * @see com.CardiacArray.data.Shift
    * */
    public Shift getShift(Date date, int userId){
        Shift shift;
        Shift shiftFromQuery;

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDate = simpleDate.format(date);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name\n" +
                "FROM shift\n" +
                "    JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "    JOIN user ON user_shift.user_id = user.user_id\n" +
                "WHERE shift.date = '" + ? + "' AND user_shift.user_id = " + ? + ";";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDate);
            statement.setInt(2, userId);
            res = statement.executeQuery();

            if (!res.next()) {
                return null;
            } else {
                Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                Date endDateFormatted = new Date(endTimeFromQuery.getTime() + endTimeFromQuery.getTime());

                shiftFromQuery = new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user"));
                res.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }

        shift = shiftFromQuery;
        return shift;
    }
}
