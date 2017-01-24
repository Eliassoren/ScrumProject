package com.CardiacArray.db;

import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


/**
 * Created by Vegard on 12/01/2017.
 */
public class ShiftDb extends DbManager{

    private ResultSet res;
    private PreparedStatement statement;

    public ShiftDb() throws Exception {
        super();
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
    public Shift getShift(java.util.Date date, int userId){
        Shift shift = null;

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        simpleDate.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
        String onlyDate = simpleDate.format(date);

        String sql = "SELECT shift.shift_id, shift.date, shift.start, shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable,\n" +
                "    user.user_id, concat_ws(' ', user.first_name, user.last_name) AS user_name,  user_category.type\n" +
                "FROM shift\n" +
                "    JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "    JOIN user ON user_shift.user_id = user.user_id\n" +
                "JOIN user_category on shift.user_category_id = user_category.user_category_id" +
                "WHERE shift.date = ? AND user_shift.user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDate);
            statement.setInt(2, userId);
            res = statement.executeQuery();

            if (!res.next()) {
                return null;
            } else {
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime());

                shift = new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user"),
                        res.getString("type"));
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
     * @author Erik
     * @param shiftId
     * @return shift
     */
    public Shift getShift(int shiftId){
        Shift shift = null;
        String sql = "SELECT shift.shift_id, shift.date, shift.start," +
                "                shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable, user.user_id, user_category.type," +
                "                concat_ws(' ', user.first_name, user.last_name) AS user_name FROM shift" +
                "        LEFT JOIN user_shift ON shift.shift_id = user_shift.shift_id" +
                "        LEFT JOIN user ON user_shift.user_id = user.user_id" +
                "        JOIN user_category on shift.user_category_id = user_category.user_category_id" +
                " WHERE shift.shift_id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, shiftId);
            res = statement.executeQuery();

            if (!res.next()) {
                return null;
            } else {
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

                shift = new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user"),
                        res.getString("type"));
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
        }

        return shift;
    }

    public ArrayList<Shift> getShiftByCategory(int user_id, int user_category_id){
        ArrayList<Shift> shiftArray = new ArrayList<>();
        String sql = "SELECT shift.shift_id, shift.date, shift.start,\n" +
                "  shift.end, shift.department_id, shift.user_category_id,\n" +
                "  shift.responsible_user, shift.tradeable, user.user_id,\n" +
                "  concat_ws(' ', user.first_name, user.last_name) AS user_name FROM shift\n" +
                "  LEFT JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "  LEFT JOIN user ON user_shift.user_id = user.user_id\n" +
                "WHERE  user.user_id = ? AND shift.user_category_id = ? ";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,user_id);
            statement.setInt(2, user_category_id);
            res = statement.executeQuery();

            while (res.next()) {
                java.sql.Date dateFromQuery = res.getDate("date");
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
                        res.getBoolean("responsible_user"),
                        res.getString("type")
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
     * Returns a list of shifts for a user in  a given period
     *
     * @author Vegard Stenvik
     * @param dateStart the start date for the shifts
     * @param dateEnd the end date for the shifts
     * @param userId the id that identifies the user
     * @see com.CardiacArray.data.Shift
     * @return list of shifts for a user in  a given period
     * */
    public ArrayList<Shift> getShiftsForPeriod(java.util.Date dateStart, java.util.Date dateEnd, int userId){
        ArrayList<Shift> shiftArray = new ArrayList<>();

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String onlyDateStart= simpleDate.format(dateStart);
        String onlyDateEnd = simpleDate.format(dateEnd);

        String sql = "SELECT shift.shift_id, shift.date, shift.start,\n" +
                "shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable, user.user_id, user_category.type,\n" +
                "concat_ws(' ', user.first_name, user.last_name) AS user_name FROM shift\n" +
                "LEFT JOIN user_shift ON shift.shift_id = user_shift.shift_id\n" +
                "LEFT JOIN user ON user_shift.user_id = user.user_id\n" +
                "LEFT JOIN user_category ON shift.user_category_id = user_category.user_category_id\n" +
                "WHERE shift.date >= ? AND shift.date <= ? AND user.user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDateStart);
            statement.setString(2, onlyDateEnd);
            statement.setInt(3, userId);
            res = statement.executeQuery();

            while (res.next()) {
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime() + 3600000L);
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime() + 3600000L);

                shiftArray.add(new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user"),
                        res.getString("type")
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
    public ArrayList<Shift> getShiftsForPeriod(java.util.Date dateStart, java.util.Date dateEnd){
        ArrayList<Shift> shiftArray = new ArrayList<>();

        // Formats date to form yyyy-MM-dd
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String onlyDateStart= simpleDate.format(dateStart);
        String onlyDateEnd = simpleDate.format(dateEnd);

        String sql = "SELECT shift.shift_id, shift.date, shift.start," +
                "                shift.end, shift.department_id, shift.user_category_id, shift.responsible_user, shift.tradeable, user.user_id, user_category.type," +
                "                concat_ws(' ', user.first_name, user.last_name) AS user_name FROM shift" +
                "        LEFT JOIN user_shift ON shift.shift_id = user_shift.shift_id" +
                "        LEFT JOIN user ON user_shift.user_id = user.user_id" +
                "        JOIN user_category on shift.user_category_id = user_category.user_category_id" +
        "  WHERE shift.date >= ? AND shift.date <= ? ";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, onlyDateStart);
            statement.setString(2, onlyDateEnd);
            res = statement.executeQuery();

            while (res.next()) {
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime());

                shiftArray.add(new Shift(
                        res.getInt("shift_id"),
                        startDateFormatted,
                        endDateFormatted,
                        res.getInt("user_id"),
                        res.getString("user_name"),
                        res.getInt("department_id"),
                        res.getInt("user_category_id"),
                        res.getBoolean("tradeable"),
                        res.getBoolean("responsible_user"),
                        res.getString("type")
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

    /*
    * Converts Date to HH:mm
    * @author Vegard Stenvik
    * @param date The date to convert to time formatted HH:mm
    * */
    private String DateToSQLTimeString(java.util.Date date){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }


    /**
     * @Erik Kjosavik
     * @param shift
     * @return Success
     */
    public boolean updateShift(Shift shift) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = false;
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
            success = true;
            }
            catch (SQLException e) {
                e.printStackTrace();
                DbManager.rollback();
                return success;
        }
        return success;
    }

    public boolean assignShift(int shiftId, int userId) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = false;

        String sql = "INSERT INTO user_shift (user_id, shift_id) VALUES (?,?)";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, shiftId);
            statement.execute();
            connection.commit();
            statement.close();
            success = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
            return success;
        }
        return success;
    }

    /**
     * Methode used to create new shifts which are not saved in the database.
     * @author Erik Kjosavik
     * @param shift
     * @return Id generated by the database, or -1 if an error occurs.
     * @see Shift
     */
    public int createShift(Shift shift){
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDate.format(shift.getStartTime());
        int returnValue = -1;

        String sql = "insert into shift " +
                "(shift_id, date, start, shift_id.end, department_id, user_category_id, tradeable, responsible_user)\n" +
                "VALUES (DEFAULT , ?,?,?,?,?,?)";

        try {
            statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(shift.getStartTime().getTime()));
            statement.setString(2, DateToSQLTimeString(shift.getStartTime()));
            statement.setString(3, DateToSQLTimeString(shift.getEndTime()));
            statement.setInt(4, shift.getDepartmentId());
            statement.setInt(5, shift.getRole());
            statement.setBoolean(6, shift.isResponsibleUser());
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

    /**
     * Adds a user to a shift.
     * @param shift
     * @param user
     * @return Success
     */
    public boolean setUser(Shift shift, User user) {
        String sql = "UPDATE user_shift SET user_id = ? WHERE shift_id = ?";
        boolean success = false;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setInt(2, shift.getShiftId());
            statement.execute();
            connection.commit();
            statement.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            DbManager.rollback();
            return success;
        }
        return success;
    }

    public boolean sendChangeRequest(int shiftId, int userId){
        boolean returnValue = false;
        String toSQL = "INSERT INTO changeover (shift_id, new_user_id, approved) values (?, ? , 0)";
        try{
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, shiftId);
            statement.setInt(2, userId);
            int status = statement.executeUpdate();
            res = statement.getGeneratedKeys();
            if(status != 0){
                returnValue = true;
            }
            res.close();
            statement.close();
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return returnValue;
    }

    public ArrayList<Shift> getChangeRequest(){
        String toSQL = "SELECT * FROM changeover WHERE approved = 0";
        ArrayList<Shift> al = new ArrayList<>();
        try {
            statement = connection.prepareStatement(toSQL);
            res = statement.executeQuery();
            while(res.next()){
                int shiftId = res.getInt("shift_id");
                int newUserId = res.getInt(("new_user_id"));
                Shift shift = new Shift();
                shift.setShiftId(shiftId);
                shift.setUserId(newUserId);
                al.add(shift);
            }
            res.close();
            statement.close();
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return al;
    }

    public boolean setApproved(int shiftId){
        boolean returnValue = false;
        String toSQL = "UPDATE changeover set approved = 1 WHERE shift_id  = ?";
        try{
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, shiftId);
            int status = statement.executeUpdate();
            if (status != 0){
                returnValue = true;
            }
            res.close();
            statement.close();
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return returnValue;
    }



    public static void main(String args[]) throws Exception {
        ShiftDb db = new ShiftDb();
        Shift shift = db.getShift(11);
        int newUserId = 6;
        System.out.println(db.sendChangeRequest(shift.getShiftId(), 6));
        ArrayList<Shift> al = db.getChangeRequest();
        for (Shift shift1 : al){
            System.out.println(shift1.getShiftId() + " " + shift1.getUserId());
        }
        System.out.print(db.setApproved(11));

    }


}

