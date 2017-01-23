package com.CardiacArray.dataTests;

import com.CardiacArray.data.Shift;
import junit.framework.TestCase;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vegard on 16/01/2017.
 */
public class ShiftTest {

    @Test
    public void testShiftTimeZone(){
        Shift shift = new Shift(new Date(1484562750734L), new Date(1484562750734L + 3600000L), 0,0, false, "Sykepleier");
        Shift shift2 = new Shift(new Date(1484574579113L), new Date(1484574579113L + 3600000L), 0, 0, false, "Sykepleier");
        assertTrue(shift2.getStartTime().toString().equals("Mon Jan 16 14:49:39 CET 2017"));
        assertEquals("Mon Jan 16 11:32:30 CET 2017", shift.getStartTime().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShiftValidTime(){
        Shift shift = new Shift(new Date(1484562750734L + 3600000L), new Date(1484562750734L), 0,0, false, "Sykepleier");
    }
}
