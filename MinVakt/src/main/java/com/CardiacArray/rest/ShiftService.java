
package com.CardiacArray.rest;

import com.CardiacArray.Mail.Mail;
import com.CardiacArray.data.Changeover;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.OvertimeDb;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
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
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
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
    public Collection<Shift> getTradeable(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime) {
        if (startTime > endTime) throw  new BadRequestException();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
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
    public Response assignShift(@PathParam("shiftId") int shiftId, @PathParam("userId") int userId) {
        if (validateShift(getShift(shiftId))) {
            Shift shift = getShift(shiftId);
            if (shift.getUserId() > 0) {
                sendChangeShiftRequest(shiftId, userId);
            }
            if (userDb.userHasShift(userId, new java.sql.Date(shift.getStartTime().getTime()))) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else if (validateShift(getShift(shiftId))) {
                shiftDb.assignShift(shift.getShiftId(), userId);
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    /**
     * Creating new shift
     *
     * @param shift-object to be created
     * @return  a negative number if the shift was not created. shiftId if it was created
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Shift createShift(Shift shift) {
        if(!validateShift(shift)){
            System.out.println("Tried creating invalid shift");
            throw new BadRequestException();
        }
        if (shift.getUserId() == 0) shift.setTradeable(true);
        if(shiftDb.createShift(shift)) {
            System.out.println("Create shift: " + shift.getShiftId());
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, 4); // Checks if shift is within the next three days
            Date date = calendar.getTime();

            if(shift.getStartTime().before(date)) {
                String email = "Hei./nDet har blitt satt opp et ledig skift på din avdeling i løpet av de neste tre dagene./nHilsen MinVakt.";
                ArrayList<User> users = userDb.getUsersByDepartmentId(shift.getDepartmentId());
                for (User user : users) {
                    Mail.sendMail(user.getEmail(), "Melding om ledig skift.", email);
                }
            }
            return shift;
        } else {
            throw new BadRequestException();
        }
    }

    /**
     *
     * @param startTime start time for the period
     * @param endTime end time for the period
     * @param userId id of the user
     * @return a collection of shifts which are tradeable
     */
    @GET
    @Path("/tradeable/{startTime}/{endTime}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getTradeable(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime, @PathParam("userId") int userId){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
        if (startTime > endTime) throw  new BadRequestException();
        User user = userDb.getUserById(userId);
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

    /**
     *
     * @param userId id of the user
     * @param userCategoryId category id of the user
     * @return a collection of shifts filtered by category
     */
    @GET
    @Path("/filter/{userId}/{userCategoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> filterShift(@PathParam("userId") int userId,@PathParam("userCategoryId") int userCategoryId) {
        Map<Shift, Shift> map = new HashMap<>();
        if (userCategoryId < 0 || userId < 0) throw  new BadRequestException();
        ArrayList<Shift> shifts = shiftDb.getShiftByCategory(userId,userCategoryId);
        for(Shift shift : shifts){
            map.put(shift,shift);
        }
        return map.values();
    }

    /**
     *
     * @param shift a Shift object
     * @return true if overtime is approved
     */
    @POST
    @Path("/approveOvertime")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approveOvertime(Shift shift) {
        if(validateShift(shift) && overtimeDb.approve(shift)) {
            User user = userDb.getUserById(shift.getUserId());
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd.mm.yyyy");
            SimpleDateFormat simpleTime = new SimpleDateFormat("HH.mm");
            simpleDate.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
            simpleTime.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
            String date = simpleDate.format(shift.getStartTime());
            String startTime = simpleDate.format(shift.getStartTime());
            String endTime = simpleDate.format(shift.getEndTime());
            String email = "Hei./nDin overtid " + date + " fra + " + startTime  + " til " + endTime + " er godkjent/nHilsen MinVakt.";
            Mail.sendMail(user.getEmail(), "Godkjent overtid", email);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    public boolean deleteApprovedShift(){
        return shiftDb.deleteApproved();
    }

    /**
     *
     * @param changeoverShift a Changeover object
     * @return true if changeover is approved
     */
    @PUT
    @Path("/approveChange")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approveChangeover(Changeover changeoverShift){
        //TODO Slette godkjenning fra databasen

        //Setter shift til approved og blir borte fra "til godkjenning"
        shiftDb.setApproved(changeoverShift.getShiftId());
        User newUser = userDb.getUserById(changeoverShift.getNewUserId());
        //finner det akutelle skiftet og endrer nødvendig data til nye bruker
        Shift updatedShift = shiftDb.getShift(changeoverShift.getShiftId());
        User oldUser = userDb.getUserById(updatedShift.getUserId());
        updatedShift.setUserId(changeoverShift.getNewUserId());
        updatedShift.setUserName(changeoverShift.getNewUser());
        updatedShift.setTradeable(false);
        if(shiftDb.updateShift(updatedShift)) {
            String email = "Hei./nDitt skift er nå gitt til " + newUser.getFirstName() + " " + newUser.getLastName() + "./mHilsen Minvakt.";
            Mail.sendMail(oldUser.getEmail(), "Endring av vakt", email);
            email = "Hei./nDu har nå tatt over skiftet til " + oldUser.getFirstName() + " " + oldUser.getLastName() + "./nHilsen Minvakt.";
            Mail.sendMail(newUser.getEmail(), "Endring av vakt", email);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     *
     * @param shiftId id of the shift
     * @param userId id of the user
     * @return true if change request is ok
     */
    @POST
    @Path("/changeover/{shiftId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendChangeShiftRequest(@PathParam("shiftId") int shiftId, @PathParam("userId") int userId){
        if(shiftId < 0 || userId < 0){
           return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(shiftDb.sendChangeRequest(shiftId,userId)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     *
     * @return collection of users who want to do a shift changeover
     */
    @GET
    @Path("/changeover")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Changeover> getChangeShiftRequest(){
        Map<Changeover, Changeover> map = new HashMap<>();
        ArrayList<Shift> foundChangeovers = shiftDb.getChangeRequest();

        for(Shift shift : foundChangeovers){
            User oldUser = userDb.getUserById(shiftDb.getShift(shift.getShiftId()).getUserId());
            User newUser = userDb.getUserById(shift.getUserId());

            Changeover tempChangeover = new Changeover(oldUser,newUser,shift.getShiftId());
            map.put(tempChangeover,tempChangeover);
        }
        return map.values();
    }

    /**
     *
     * @return a collection of all overtime requests
     */
    @GET
    @Path("/overtime/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Shift> getAllOvertimeRequests() throws Exception {
        Map<Shift,Shift> map = new HashMap<>();
        ArrayList<Shift> al = overtimeDb.getAllOvertime();
        for (Shift shift : al){
            map.put(shift,shift);
        }
        return map.values();
    }

    /**
     *
     * @param shift a Shift object
     * @return false if a shift is not valid
     */
    private boolean validateShift(Shift shift){
        Date start = shift.getStartTime();
        Date end = shift.getEndTime();

        if(start != null && end != null){
            return end.after(start);
        }
        return false;
    }

    public static void main(String args[]) throws Exception {
        ShiftDb s = new ShiftDb();
        s.assignShift(3,16);
    }
}
