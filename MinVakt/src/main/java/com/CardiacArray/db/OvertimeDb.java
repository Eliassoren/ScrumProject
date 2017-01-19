package com.CardiacArray.db;

import com.CardiacArray.data.Shift;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kjosavik on 19-Jan-17.
 */
public class OvertimeDb extends DbManager {

    private ResultSet res;
    private PreparedStatement statement;

    public OvertimeDb() throws Exception{
        super();
    }

    public Shift getOvertime(Shift originalShift){
        int shiftId = originalShift.getShiftId();

        String toSql = "SELECT overtime.start, overtime.end  FROM overtime WHERE shift_id = ? ";
        try{
            statement = connection.prepareStatement(toSql);
            statement.setInt(1, shiftId);
            res = statement.executeQuery();

            if(!res.next()) {
                System.out.println("null");
                return null;
            }else {
                Time startTimeOvertime = res.getTime("start");
                Time endTimeOvertime = res.getTime("end");
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(originalShift.getStartTime());
                int startDay = calendar.get(Calendar.DATE);
                calendar.setTime(originalShift.getEndTime());
                int endDay = calendar.get(Calendar.DATE);
                if (startDay == endDay) {
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    //originalShift.setStartTime(Integer.parseInt(year) + Integer.parseInt(month) + Integer.parseInt(endDay));
                    System.out.println(Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(endDay));
                    //TODO Få originalShift.setStartTime på riktig format. Det skal være en Long. Husk å legge til de timene med overtid.
                }else {
    System.out.print("nope");
                }
            }
        }catch (SQLException e){
                e.printStackTrace();
        }
        return originalShift;
    }

    public static void main(String[] args) throws Exception{
        ShiftDb shiftDb = new ShiftDb();
        Shift shift = shiftDb.getShift(10);
        OvertimeDb test = new OvertimeDb();
        Shift newShift = test.getOvertime(shift);
    }
}
