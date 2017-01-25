
package com.CardiacArray.rest;

import com.CardiacArray.data.Changeover;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.OvertimeDb;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/shifts")
public class ShiftService {

    private ShiftDb shiftDb = new ShiftDb();
    private UserDb userDb = new UserDb();
    private OvertimeDb overtimeDb = new OvertimeDb();

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
        System.out.println(new Date(startTime) + " " + new Date(endTime));
        Map<Shift, Shift> map = new HashMap<>();
        for(Shift shiftElement: shifts){
            if(shiftElement.isTradeable()){
                map.put(shiftElement,shiftElement);
                System.out.println(shiftElement);
            } else {
                System.out.println("Shift not tradeable");
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
     * @return boolean value true if successful
     */
    @POST
    @Path("/assign/{shiftId}/{userId}")
    @Consumes (MediaType.APPLICATION_JSON)
    public Response assignShift(@PathParam("shiftId") int shiftId, @PathParam("userId") int userId) {
        Shift shift = getShift(shiftId);
        if(userDb.userHasShift(userId, new java.sql.Date(shift.getStartTime().getTime()))) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else if(validateShift(getShift(shiftId))) {
            shiftDb.assignShift(shift.getShiftId(), userId);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Createing new shift
     *
     * @param shift-object to be created
     * @return  a negative number if the shift was not created. shiftId if it was created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createShift(Shift shift) {
        if(!validateShift(shift)){
            throw new BadRequestException();
        }
        int responseId = shiftDb.createShift(shift);
        if(responseId < 0) throw new BadRequestException();
        else return Response.ok().build();
    }

    @GET
    @Path("/tradeable/{startTime}/{endTime}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getTradeable(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime, @PathParam("userId") int userId){
        if (startTime > endTime) throw  new BadRequestException();
        User user = userDb.getUserByEmail(userId);
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime));
        System.out.println("Datoer " + new Date(startTime) + " " + new Date(endTime));
        Map<Shift, Shift> map = new HashMap<>();
        for(Shift shiftElement: shifts){
            if(shiftElement.isTradeable() && shiftElement.getRole() == user.getUserCategoryInt()){
                map.put(shiftElement,shiftElement);
                System.out.println(shiftElement);
            } else {
                System.out.println("H");
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


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean approveOvertime(Shift shift){
        boolean approvedResponse = false;
        if(validateShift(shift)){
            approvedResponse = overtimeDb.aprove(shift);
        }else{
            throw new BadRequestException();
        }
        return approvedResponse;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/approveChange")
    public boolean approveChangeover(Changeover changeoverShift){

        //Setter shift til approved og blir borte fra "til godkjenning"
        shiftDb.setApproved(changeoverShift.getShiftId());

        //finner det akutelle skiftet og endrer nødvendig data til nye bruker
        Shift updatedShift = shiftDb.getShift(changeoverShift.getShiftId());
        updatedShift.setUserId(changeoverShift.getNewUserId());
        updatedShift.setUserName(changeoverShift.getNewUser());
        updatedShift.setTradeable(false);

        boolean response = shiftDb.updateShift(updatedShift);
        return response;
    }

    @POST
    @Path("/changeover/{shiftId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean sendChangeShiftRequest(@PathParam("shiftId") int shiftId, @PathParam("userId") int userId){
        if(shiftId < 0 || userId < 0){
            throw new BadRequestException();
        }
        boolean changeoverResponse = shiftDb.sendChangeRequest(shiftId,userId);
        return changeoverResponse;
    }

    @GET
    @Path("/changeover")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Changeover> getChangeShiftRequest(){
        Map<Changeover, Changeover> map = new HashMap<>();
        ArrayList<Shift> foundChangeovers = shiftDb.getChangeRequest();

        for(Shift shift : foundChangeovers){
            User oldUser = userDb.getUserByEmail(shiftDb.getShift(shift.getShiftId()).getUserId());
            User newUser = userDb.getUserByEmail(shift.getUserId());

            Changeover tempChangeover = new Changeover(oldUser,newUser,shift.getShiftId());
            map.put(tempChangeover,tempChangeover);
        }
        return map.values();
    }

    @GET
    @Path("/overtime")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getAllOvertimeRequests(){
        Map<Shift,Shift> map = new HashMap<>();
        ArrayList<Shift> al = overtimeDb.getAllOvertime();
        for (Shift shift : al){
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
