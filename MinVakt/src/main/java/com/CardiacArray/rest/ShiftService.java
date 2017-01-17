
package com.CardiacArray.rest;

import com.CardiacArray.data.Shift;
import com.CardiacArray.db.ShiftDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Path("/shifts")
public class ShiftService {

    private ShiftDb shiftDb = new ShiftDb();

    public ShiftService() throws Exception {}
/*
    @GET
    @Path("/{date}/{shiftId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shift getShift(Date date, @PathParam("shiftId") int shiftId) {
        return shiftDb.getShift(date,shiftId);
    }

    @GET
    @Path("/{shiftId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shift getShift(@PathParam("shiftId") int shiftId) {
        return shiftDb.getShift(shiftId);
    }

    /**
     * Returns a list of shifts for a user in  a given period
     *
     * @param dateStart the start date for the shifts
     * @param dateEnd the end date for the shifts
     * @param userId the id that identifies the user
     * @return ArrayList of found shifts
     */
/*
    @GET
    @Path("/{dateStart}/{dateEnd}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Shift> getShift(Date dateStart, Date dateEnd,@PathParam("userId") int userId) {
        if (dateEnd.before(dateStart)){
            throw  new BadRequestException();
        }
        return shiftDb.getShiftsForPeriod(dateStart,dateEnd,userId);
    }
*/
    /**
     *
     * Returns a list of shifts for in a given period
     *
     * @param dateStart the start date for the shifts
     * @param dateEnd the end date for the shifts
     * @return ArrayList of found shifts
     */
/*
    @GET
    @Path("/{dateStart}/{dateEnd}")
    @Produces(MediaType.APPLICATION_JSON)/*
    public ArrayList<Shift> getShift(Date dateStart, Date dateEnd) {
        if (dateEnd.before(dateStart)){
            throw  new BadRequestException();
        }
        return shiftDb.getShiftsForPeriod(dateStart,dateEnd);
    }*/

    /**
     * Updates an existing shift
     *
     * @param shift-object with updated infomation
     * @return boolean value if shift correctly updated
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateShift(Shift shift) {
        if(validateShift(shift)){
            return shiftDb.updateShift(shift);
        }
       throw new BadRequestException();
    }*/

    /**
     *
     * @param shift-object to be created
     * @return  a negative number if the shift was not created. shiftId if it was created
     */
   /* @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int createShift(Shift shift) {
        if(!validateShift(shift)){
            throw new BadRequestException();
        }

        int responseId = shiftDb.createShift(shift);
        if(responseId < 0) throw new BadRequestException();
        else return responseId;
    }

    private boolean validateShift(Shift shift){
        Date start = shift.getStartTime();
        Date end = shift.getEndTime();

        if(start != null && end != null){
            if(!(end.after(start))){
                return false;
            }
            return true;
        }
        return false;
    }*/
}
