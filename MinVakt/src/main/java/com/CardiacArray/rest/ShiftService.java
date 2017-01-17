
package com.CardiacArray.rest;

import com.CardiacArray.data.Shift;
import com.CardiacArray.db.ShiftDb;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;


@Path("/shifts")
public class ShiftService {

    private ShiftDb shiftDb = new ShiftDb();

    public ShiftService(ShiftDb shiftDb) throws Exception {
        this.shiftDb = shiftDb;
    }

    public ShiftService() throws Exception {}

    /**
     *
     * @param shiftId
     * @return the chosen shift object
     */
    @GET
    @Path("/{shiftId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shift getShift(@PathParam("shiftId") int shiftId) {
        if(shiftId < 0){
            throw new BadRequestException();
        }
        return shiftDb.getShift(shiftId);
    }

    /**
     * Returns a list of shifts for a user in  a given period
     * @param shift shiftobject with start and end time
     * @param userId the id that identifies the user
     * @return ArrayList of found shifts
     */
    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Shift> getShift(Shift shift, @PathParam("userId") int userId) {
        if (shift.getEndTime().before(shift.getStartTime())){
            throw  new BadRequestException();
        }
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(shift.getStartTime(),shift.getEndTime(),userId);
        Map<Shift, Shift> map = new HashMap<>();
        for (Shift shiftElement : shifts) {
            map.put(shiftElement, shiftElement);
        }
        return map.values();
    }

    /**
     *
     * Returns a list of shifts for in a given period
     *
     * @param shift the end date for the shifts
     * @return ArrayList of found shifts
     */
    @GET
    @Path("/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Shift> getShift(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime) {
        if (startTime > endTime) throw  new BadRequestException();
        return shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime));
    }

    /**
     * Updates an existing shift
     *
     * @param shift-object with updated infomation
     * @return boolean value if shift correctly updated
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateShift(Shift shift) {
        if(validateShift(shift)){
            return shiftDb.updateShift(shift);
        }
       throw new BadRequestException();
    }

    /**
     *
     * @param shift-object to be created
     * @return  a negative number if the shift was not created. shiftId if it was created
     */
    @POST
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
    }
}
