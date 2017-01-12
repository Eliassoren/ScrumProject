package com.CardiacArray.db;

import com.CardiacArray.data.Shift;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /**
    * Returns a single shift object
    *
    * @author Vegard Stenvik
    * @param date the date for the shift
    * @param userId the id that identifies the user
    * @see com.CardiacArray.data.Shift
    * @return Returns single shift object
    * */
    public Shift getShift(Date date, int userId){
        Shift shift;
        Shift shiftFromQuery = null;

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDate = simpleDate.format(date);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name\n" +
                "FROM shift\n" +
                "    JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "    JOIN user ON user_shift.user_id = user.user_id\n" +
                "WHERE shift.date = ? AND user_shift.user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDate);
            statement.setInt(2, userId);
            res = statement.executeQuery();

            if (!res.next()) {
                System.out.println("Fudge");
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



    /**
     * Returns a list of shifts for a user in  a given period
     *
     * @author Vegard Stenvik
     * @param dateStart the start date for the shifts
     * @param dateEnd the end date for the shifts
     * @param userId the id that identifies the user
     * @see com.CardiacArray.data.Shift
     * @return list of shifts for a user in  a given period
     * */
    public ArrayList<Shift> getShiftsForPeriod(Date dateStart, Date dateEnd, int userId){
        ArrayList<Shift> shiftArray = new ArrayList<>();

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDateStart= simpleDate.format(dateStart);
        String onlyDateEnd = simpleDate.format(dateEnd);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name\n" +
                "FROM shift\n" +
                "    JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "    JOIN user ON user_shift.user_id = user.user_id\n" +
                "WHERE shift.date >= ? AND shift.date <= ? AND user_shift.user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDateStart);
            statement.setString(2, onlyDateEnd);
            statement.setInt(3, userId);
            res = statement.executeQuery();

            while (res.next()) {
                Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                Date endDateFormatted = new Date(endTimeFromQuery.getTime() + endTimeFromQuery.getTime());

                shiftArray.add(new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user")
                ));
            }

            res.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }

        return shiftArray;
    }

    /**
     * Returns a list of shifts for in a given period
     *
     * @author Vegard Stenvik
     * @param dateStart the start date for the shifts
     * @param dateEnd the end date for the shifts
     * @see com.CardiacArray.data.Shift
     * @return list of shifts for in a given period
     * */
    public ArrayList<Shift> getShiftsForPeriod(Date dateStart, Date dateEnd){
        ArrayList<Shift> shiftArray = new ArrayList<>();

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDateStart= simpleDate.format(dateStart);
        String onlyDateEnd = simpleDate.format(dateEnd);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name\n" +
                "FROM shift\n" +
                "    JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "    JOIN user ON user_shift.user_id = user.user_id\n" +
                "WHERE shift.date >= ? AND shift.date <= ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDateStart);
            statement.setString(2, onlyDateEnd);
            res = statement.executeQuery();

            while (res.next()) {
                Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                Date endDateFormatted = new Date(endTimeFromQuery.getTime() + endTimeFromQuery.getTime());

                shiftArray.add(new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user")
                ));
            }

            res.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }

        return shiftArray;
    }

    public static void main(String args[]) throws Exception {
        Date d = new Date(1483225200000L);
        Date e = new Date(1484438400000L);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDate.format(d);
        String es = simpleDate.format(e);
        System.out.println(s);
        System.out.println(es);
        DbManager db = new DbManager();
        ShiftDb shiftDb = new ShiftDb(db.connection);
        ArrayList<Shift> a = shiftDb.getShiftsForPeriod(d, e, 1);

        for (Shift shifttet: a) {
            System.out.println(shifttet);
        }

    }

}
