package com.CardiacArray.db;

import com.CardiacArray.data.Shift;

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
            res.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public ArrayList<Shift> getAllOvertime(){
        ArrayList<Shift> al = new ArrayList<>();
        String toSQL = "Select overtime.shift_id, user.first_name, user.last_name, overtime.start, overtime.end\n" +
                "  from overtime\n" +
                "left join user_shift on user_shift.shift_id = overtime.shift_id\n" +
                "LEFT join user on user.user_id = user_shift.user_id\n" +
                "where overtime.approved = 0";
        try{
            statement = connection.prepareStatement(toSQL);
            res = statement.executeQuery();
            while (res.next()){
                Shift shift = new Shift();
                shift.setShiftId(res.getInt("shift_id"));
                shift.setUserName(res.getString("first_name") + " " + res.getString("last_name"));
                shift.setStartTimeTime((res.getTime("start")));
                shift.setEndTimeTime((res.getTime("end")));
                al.add(shift);
            }
            res.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return al;
    }


    public static void main(String[] args) throws Exception{
        ShiftDb shiftDb = new ShiftDb();
        Shift shift = shiftDb.getShift(17);
        OvertimeDb test = new OvertimeDb();
        Time start = new Time(shift.getEndTime().getTime());
        Time end = new Time(shift.getEndTime().getTime() + (2 * 3600000));

        System.out.println("Set = " + test.setOvertime(shift, start, end));
        System.out.println("milistart = " + start);
        System.out.println("miliend = " + end);

        System.out.println("Get = " + shift.equals(test.getOvertime(shift)));
        System.out.println("Approve = " + test.approve(shift));

        System.out.println(test.deleteOvertime(shift));
    }
}
