
package com.CardiacArray.rest;

import com.CardiacArray.AuthFilter.Role;
import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/shifts")
public class ShiftService {

    private ShiftDb shiftDb = new ShiftDb();
    private UserDb userDb = new UserDb();

    public ShiftService(ShiftDb shiftDb) throws Exception {
        this.shiftDb = shiftDb;
    }

    /**
     * Empty constructor
     *
     * @throws Exception
     */
    public ShiftService() throws Exception {}

    /**
     * Returns the shift related to id
     *
     * @param shiftId the id that identifies the shift
     * @return the chosen shift object
     */
    @GET
    @Path("/{shiftId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shift getShift(@PathParam("shiftId") int shiftId) {
        if(shiftId < 0){
            throw new BadRequestException();
        }
        System.out.println("Getting shift " + shiftId + ": " + shiftDb.getShift(shiftId));
        return shiftDb.getShift(shiftId);
    }

    /**
     * Returns a list of shifts for a user in  a given period
     *
     * @param startTime start time
     * @param userId the id that identifies the user
     * @return ArrayList of found shifts
     */
    @GET
    @Path("/{startTime}/{endTime}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getShift(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime, @PathParam("userId") int userId) {
        if (startTime > endTime) throw new BadRequestException();
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime),userId);
        Map<Shift, Shift> map = new HashMap<>();
        for (Shift shiftElement : shifts) {
            map.put(shiftElement, shiftElement);
        }
        return map.values();
    }

    /**
     * Returns a list of shifts for in a given period
     *
     * @param startTime the start date for the shifts
     * @param endTime the end date for the shifts
     * @return Collection of found shifts
     */
    @GET
    @Path("/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getShift(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime) {
        if (startTime > endTime) throw  new BadRequestException();
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime));
        Map<Shift, Shift> map = new HashMap<>();
        for (Shift shiftElement : shifts){
            map.put(shiftElement, shiftElement);
        }
        return map.values();
    }

    /**
     * Returns a list of all tradeable shifts
     *
     * @param startTime the start date for the shifts
     * @param endTime the end date for the shiftsdTime
     * @return  a collection with all tradeable shifts in a given period
     */
    @GET
    @Path("/tradeable/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getTradeable(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime){
        if (startTime > endTime) throw  new BadRequestException();
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime));
        Map<Shift, Shift> map = new HashMap<>();
        for(Shift shiftElement: shifts){
            if(shiftElement.isTradeable()){
                map.put(shiftElement,shiftElement);
            }
        }
        return map.values();
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
     * Assigns a shift to the user
     * @param shiftId id of the shift
     * @param userId id of the user
     * @return boolean value true if successful
     */
    @POST
    @Path("/assign/{shiftId}/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignShift(@PathParam("shiftId") int shiftId, @PathParam("userId") int userId) {
        if(validateShift(getShift(shiftId))) {
            shiftDb.assignShift(shiftId, userId);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Createng new shift
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


    //TODO getTradeable med userCategoryId

    @GET
    @Path("/tradeable/{startTime}/{endTime}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getTradeable(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime, @PathParam("userId") int userId){
        if (startTime > endTime) throw  new BadRequestException();
        User user = userDb.getUserByEmail(userId);
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime));
        Map<Shift, Shift> map = new HashMap<>();
        for(Shift shiftElement: shifts){
            if(shiftElement.isTradeable() && shiftElement.getRole() == user.getUserCategoryInt()){
                map.put(shiftElement,shiftElement);
            }
        }
        return map.values();
    }

    @GET
    @Path("/filter/{userId}/{userCategoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> filterShift(@PathParam("userId") int userId,@PathParam("userCategoryId") int userCategoryId){
        Map<Shift, Shift> map = new HashMap<>();
        if (userCategoryId < 0 || userId < 0) throw  new BadRequestException();
        ArrayList<Shift> shifts = shiftDb.getShiftByCategory(userId,userCategoryId);
        for(Shift shift : shifts){
            map.put(shift,shift);
        }
        return map.values();
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

    public static void main(String args[]) throws Exception {
        ShiftDb s = new ShiftDb();
        s.assignShift(3,16);
    }
}
