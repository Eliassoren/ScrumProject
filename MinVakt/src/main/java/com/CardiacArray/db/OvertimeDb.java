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
            }
        }catch (SQLException e){
                e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return originalShift;
    }



    public static void main(String[] args) throws Exception{
        ShiftDb shiftDb = new ShiftDb();
        Shift shift = shiftDb.getShift(10);
        OvertimeDb test = new OvertimeDb();
        Shift newShift = test.getOvertime(shift);
        System.out.println(newShift.getStartTime());
        System.out.println(newShift.getEndTime());
    }
}
