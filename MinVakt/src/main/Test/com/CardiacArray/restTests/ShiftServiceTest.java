package com.CardiacArray.restTests;

import com.CardiacArray.data.Shift;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.rest.ShiftService;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Vegard on 17/01/2017.
 */
public class ShiftServiceTest {
    private Shift shift = new Shift(new Date(1484562750734L), new Date(1484562750734L + 3600000L), 0,0, false);



    @Test
    public void getShiftTest() throws Exception {
        ShiftDb shiftDb= mock(ShiftDb.class);
        ShiftService shiftService = new ShiftService(shiftDb);
        when(shiftDb.getShift( anyObject(), eq(1))).thenReturn(shift);
    }

    @Test
    public void getShift1() throws Exception {

    }

    @Test
    public void getShift2() throws Exception {

    }

    @Test
    public void updateShift() throws Exception {

    }

    @Test
    public void createShift() throws Exception {

    }

}