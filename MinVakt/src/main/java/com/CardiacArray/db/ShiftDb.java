package com.CardiacArray.db;

import com.CardiacArray.data.Shift;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
                return null;
            } else {
                Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                Date endDateFormatted = new Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

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
     *
     * @param shitId
     * @return shift
     */
    public Shift getShift(int shitId){
        Shift shift;

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDate = simpleDate.format(date);

        String sql = "Select * from shift WHERE shift_id=?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, shitId);
            res = statement.executeQuery();

            if (!res.next()) {
                return null;
            } else {
                Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                Date endDateFormatted = new Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

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
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                Date endDateFormatted = new Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

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
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String onlyDateStart= simpleDate.format(dateStart);
        String onlyDateEnd = simpleDate.format(dateEnd);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name\n" +
                "FROM shift\n" +
                "WHERE shift.date >= ? AND shift.date <= ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDateStart);
            statement.setString(2, onlyDateEnd);
            res = statement.executeQuery();

            while (res.next()) {
                Date dateFromQuery = res.getDate("date");
                System.out.println("Date from query: " + dateFromQuery);
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                Date startDateFormatted = new Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                Date endDateFormatted = new Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

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

    private String DateToSQLTimeString(Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    public void updateShift(Shift shift){
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDate.format(shift.getStartTime());

        String sql = "UPDATE shift SET shift.date = ?,\n" +
                "    shift.start = ?,\n" +
                "    shift.end = ?,\n" +
                "    shift.department_id = ?,\n" +
                "    shift.responsible_user = ?,\n" +
                "    shift.tradeable = ?\n" +
                "WHERE shift_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(shift.getStartTime().getTime()));
            statement.setString(2, DateToSQLTimeString(shift.getStartTime()));
            statement.setString(3, DateToSQLTimeString(shift.getEndTime()));
            statement.setInt(4, shift.getDepartmentId());
            statement.setBoolean(5, shift.isResponsibleUser());
            statement.setBoolean(6, shift.isTradeable());
            statement.setInt(7, shift.getShiftId());
            statement.execute();
            connection.commit();
            statement.close();
            }
            catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }
    }

    /**
     * Methode used to create new shifts which are not saved in the database.
     * @author Erik Kjosavik
     * @param shift
     * @see Shift
     */
    public int createShift(Shift shift){
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDate.format(shift.getStartTime());
        int returnValue = -1;

        String sql = "insert into shift " +
                "(shift_id, date, start, end, department_id, user_category_id, tradeable, responsible_user)\n" +
                "VALUES (DEFAULT , ?,?,?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(shift.getStartTime().getTime()));
            statement.setString(2, DateToSQLTimeString(shift.getStartTime()));
            statement.setString(3, DateToSQLTimeString(shift.getEndTime()));
            statement.setInt(4, shift.getDepartmentId());
            statement.setInt(5, shift.getRole());
            statement.setBoolean(6, shift.isTradeable());
            statement.setBoolean(7, shift.isResponsibleUser());
            statement.execute();
            connection.commit();
            ResultSet res = statement.getGeneratedKeys();
            if(res.next()){
                returnValue = res.getInt(1);
            } else{
                return -1;
            }
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }
        return returnValue;
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
        Shift testShiftStart = new Shift(728002800000, 728037900000, 1, 1, 0, false);
        int id = shiftDb.createShift(testShiftStart);
        shiftDb.getShift(id);

    }

}
