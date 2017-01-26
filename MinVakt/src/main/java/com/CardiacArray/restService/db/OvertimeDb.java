package com.CardiacArray.restService.db;


import com.CardiacArray.restService.data.Overtime;
import com.CardiacArray.restService.data.Shift;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kjosavik on 19-Jan-17.
 */
public class OvertimeDb extends DbManager {

    private ResultSet res;
    private PreparedStatement statement;

    public OvertimeDb() throws Exception{
        super();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
    }

    /**
     *
     * @param originalShift
     * @return A shift object that is completely the same as originalShift except for the time,
     * which is the time that has been worked extra.
     */

    public Shift getOvertime(Shift originalShift){
        int shiftId = originalShift.getShiftId();

        String toSql = "SELECT overtime.start, overtime.end  FROM overtime WHERE shift_id = ? ";
        try{
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, shiftId);
            res = statement.executeQuery();

            if(!res.next()) {
                return originalShift;
            }else {
                Time startTimeOvertime = res.getTime("start");
                Time endTimeOvertime = res.getTime("end");
                Calendar calendar = GregorianCalendar.getInstance();

                calendar.setTime(originalShift.getStartTime());
                int startDay = calendar.get(Calendar.DATE);
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);

                calendar.setTime(originalShift.getEndTime());
                int endDay = calendar.get(Calendar.DATE);
                int endYear = calendar.get(Calendar.YEAR);
                int endMonth = calendar.get(Calendar.MONTH);

                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                simpleDate.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
                Date startDate = simpleDate.parse(Integer.toString(startYear) + "-" + Integer.toString(startMonth) + "-" + Integer.toString(startDay));
                Date endDate = simpleDate.parse(Integer.toString(endYear) + "-" + Integer.toString(endMonth) + "-" + Integer.toString(endDay));

                originalShift.setStartTime(Long.toString(startDate.getTime() + startTimeOvertime.getTime()));
                originalShift.setEndTime(Long.toString(endDate.getTime() + endTimeOvertime.getTime()));
                res.close();
                statement.close();
            }
        }catch (SQLException e){
                e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return originalShift;
    }

    public Overtime getOvertime(int overtimeId){

        String toSQL = "Select overtime.overtime_id, overtime.shift_id, overtime.approved, user.user_id, user.first_name, user.last_name, shift.date, overtime.start, overtime.end\n" +
                "  from overtime\n" +
                "left join user_shift on user_shift.shift_id = overtime.shift_id\n" +
                "LEFT JOIN shift ON overtime.shift_id = shift.shift_id\n" +
                "LEFT join user on user.user_id = user_shift.user_id\n" +
                "where overtime.overtime_id = ?";
        try{
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, overtimeId);
            res = statement.executeQuery();

            if(!res.next()) {
                return null;
            }else {
                int shiftId = res.getInt("shift_id");
                int userId = res.getInt("user_id");
                String firstName = res.getString("first_name");
                String lastName = res.getString("last_name");
                boolean approved = res.getBoolean("approved");
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime());
                Overtime overtime = new Overtime(overtimeId, shiftId, userId, firstName, lastName, startDateFormatted, endDateFormatted, approved);
                res.close();
                statement.close();
                return overtime;
            }
        }catch (SQLException e){
                e.printStackTrace();
        }
        return null;
    }

    public Overtime getOvertimeByShiftId(int shiftId){

        String toSQL = "Select overtime.overtime_id, overtime.shift_id, overtime.approved, user.user_id, user.first_name, user.last_name, shift.date, overtime.start, overtime.end\n" +
                "  from overtime\n" +
                "left join user_shift on user_shift.shift_id = overtime.shift_id\n" +
                "LEFT JOIN shift ON overtime.shift_id = shift.shift_id\n" +
                "LEFT join user on user.user_id = user_shift.user_id\n" +
                "where overtime.shift_id = ?";
        try{
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, shiftId);
            res = statement.executeQuery();

            if(!res.next()) {
                return null;
            }else {
                int overtimeId = res.getInt("overtime_id");
                int userId = res.getInt("user_id");
                String firstName = res.getString("first_name");
                String lastName = res.getString("last_name");
                boolean approved = res.getBoolean("approved");
                java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime());
                Overtime overtime = new Overtime(overtimeId, shiftId, userId, firstName, lastName, startDateFormatted, endDateFormatted, approved);
                res.close();
                statement.close();
                return overtime;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param shift The shift that has the overtime which you would like to delete.
     * @return True if the deletion was successful. False if an error occurred.
     */
    public boolean deleteOvertime(Shift shift) {
        int returnvalue = 0;
        String toSql = "DELETE FROM overtime WHERE shift_id = ? ";
        try {
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, shift.getShiftId());
            returnvalue = statement.executeUpdate();
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnvalue != 0;
    }

    /**
     *
     * @param shift Shift on which overtime has occurred.
     * @param fromTime  Time where overtime has started. Typically the same as the shifts endTime.
     * @param toTime Time where overtime stopped.
     * @return True if overtime was added successfully.
     */
    public boolean setOvertime(Shift shift, Time fromTime, Time toTime){
        boolean returnValue = false;
        String toSql = "INSERT INTO overtime " +
                "(overtime_id, shift_id, start, overtime.end, approved) " +
                "VALUES (DEFAULT, ?, ?, ?, 0)";

        try{
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, shift.getShiftId());
            statement.setTime(2, fromTime);
            statement.setTime(3, toTime);
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

    /**
     *
     * @param shift The shift where overtime must be approved.
     * @return True if overtime was successfully added.
     */
    public boolean approve(Shift shift){
        boolean returnValue = false;
        String toSQL = "UPDATE overtime " +
                "SET approved = 1 " +
                "where shift_id = ?";
        try {
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, shift.getShiftId());
            int status = statement.executeUpdate();
            System.out.println(status);
            if (status != 0){
                returnValue = true;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    /**
     *
     * @return A list with shifts that have overtime registered to it.
     */
    public ArrayList<Overtime> getAllOvertime(){
        ArrayList<Overtime> overtimes = new ArrayList<>();
        String toSQL = "Select overtime.overtime_id, overtime.shift_id, user.user_id, user.first_name, user.last_name, shift.date, overtime.start, overtime.end\n" +
                "  from overtime\n" +
                "left join user_shift on user_shift.shift_id = overtime.shift_id\n" +
                "LEFT JOIN shift ON overtime.shift_id = shift.shift_id\n" +
                "LEFT join user on user.user_id = user_shift.user_id\n" +
                "where overtime.approved = 0";
        try{
            statement = connection.prepareStatement(toSQL);
            res = statement.executeQuery();
            while (res.next()){
                int overtimeId = res.getInt("overtime_id");
                int shiftId  = res.getInt("shift_id");
                int userId = res.getInt("user_id");
                String firstName = res.getString("first_name");
                String lastName = res.getString("last_name");
                 java.sql.Date dateFromQuery = res.getDate("date");
                Time startTimeFromQuery = res.getTime("start");
                Time endTimeFromQuery = res.getTime("end");
                java.util.Date startDateFormatted = new java.util.Date(dateFromQuery.getTime() + startTimeFromQuery.getTime());
                java.util.Date endDateFormatted = new java.util.Date(dateFromQuery.getTime() + endTimeFromQuery.getTime());
                Overtime overtime = new Overtime(overtimeId, shiftId, userId, firstName, lastName, startDateFormatted, endDateFormatted, false);
                overtimes.add(overtime);
            }
            res.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return overtimes;
    }
}
