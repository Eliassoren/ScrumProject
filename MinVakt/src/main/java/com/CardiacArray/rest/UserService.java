package com.CardiacArray.rest;

import com.CardiacArray.AuthFilter.Role;
import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.data.*;
import com.CardiacArray.db.*;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.OvertimeDb;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 *
 * @author Team 1 
 */

@Secured({Role.ADMIN, Role.USER})
@Path("/users")
public class UserService {
    private UserDb userDb = new UserDb();
    private ShiftDb shiftDb = new ShiftDb();
    private OvertimeDb overtimeDB = new OvertimeDb();
    private AbsenceDb absenceDb = new AbsenceDb();
    private PasswordUtil passwordUtil = new PasswordUtil();

    public UserService(UserDb userDb) throws Exception {
        this.userDb = userDb;
    }

    public UserService() throws Exception {
    }

    /**
     *
     * @param id users ID
     * @return user object
     */
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") String id){
        User userFound = userDb.getUserById(Integer.parseInt(id));
        if(userFound.getFirstName() == null || userFound.getLastName() == null) throw new NotFoundException();
        else return userFound;
    }

    /**
     *
     * @param email email of the user
     * @return user object
     */

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        User userFound = userDb.getUserByEmail(email);
        if(userFound.getFirstName() == null || userFound.getLastName() == null) throw new NotFoundException();
        else return userFound;
    }

    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getAllUsers(){
        Map<User,User> map = new HashMap<>();
        ArrayList<User> allUsers = userDb.getAllUsers();
        for(User user : allUsers){
            map.put(user,user);
        }
        return map.values();
    }


    /**
     *
     * @param user user object
     * @return true if updated, throws exception if not
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(User user) {
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || !user.isValidEmail(user.getEmail())) {
            throw new BadRequestException();
        }
        boolean updateResponse = userDb.updateUser(user);
        if(!updateResponse) {
            throw new BadRequestException();
        }
        return updateResponse;
    }


    /**
     *
     * @param user
     * @return
     */
    @PUT
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteUser(User user) {
        User userFound = userDb.getUserByEmail(user.getEmail());
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
        else {
            userFound.setActive(false);
            userDb.setUserActive(userFound);
            return true;
        }
    }

    /**
     *
     * @param user user object
     * @return True if the user is created, throw exception if not
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createUser(User user) {
        User checkUser = userDb.getUserByEmail(user.getEmail());
        if(checkUser.getFirstName() == null && checkUser.getLastName() == null){

            user.setPassword(passwordUtil.hashPassword(user.getPassword(),user.getEmail()));
            userDb.createUser(user);

            return true;
        }
        else throw new BadRequestException();

    }

    @POST
    @Path("/available/{userId}/{start}/{end}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setUserAvailable(@PathParam("userId") int userId,
                                     @PathParam("start") long start, @PathParam("end") long end) {
        User user = userDb.getUserById(userId);
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || !user.isValidEmail(user.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDb.setUserAvailable(userId, start, end);
        return Response.ok().build();
    }

    @GET
    @Path("/hours/{startTime}/{endTime}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getHoursForPeriod(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime,@PathParam("userId") int userId){
        long hoursWorked = 0;
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime), userId);
        for(Shift eachShift : shifts){
            long diff = eachShift.getEndTime().getTime() - eachShift.getStartTime().getTime();
            long diffHours = diff / (60 * 60 * 1000);
            hoursWorked += diffHours;
        }
        return hoursWorked;
    }

    public long checkOvertimeforPeriod(Shift shift, long startTime, long endTime){
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime),shift.getUserId());
        long totOvertimeHours = 0;
        for(Shift eachShift : shifts) {
            Shift overtimeShift = overtimeDB.getOvertime(eachShift);
            if (!eachShift.equals(overtimeShift)){
                long diffOvertime = overtimeShift.getEndTime().getTime()-overtimeShift.getStartTime().getTime();
                totOvertimeHours += diffOvertime / (60 * 60 * 1000);
            }
        }
        return totOvertimeHours;
    }

    @GET
    @Path("/overtime/{userId}/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public String [][] getOvertimeForPeriod(@PathParam("userId") int userId, @PathParam("startTime") long startTime, @PathParam("endTime") long endTime){

        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime),userId);

        String[][]overtimeArray = new String[shifts.size()][2];

        for(int i = 0; i < shifts.size(); i++){
            Shift overtimeShift = overtimeDB.getOvertime(shifts.get(i));
            if(!overtimeShift.equals(shifts.get(i))){
                long overtimeInHours = overtimeShift.getEndTime().getTime()-overtimeShift.getStartTime().getTime();
                String start = overtimeShift.getStartTime().toString();
                String end = overtimeShift.getEndTime().toString();
                overtimeArray[i][0] = start+end;
                overtimeArray[i][1] = Long.toString(overtimeInHours);
            }
        }
       return overtimeArray;
    }

    /**
     *
     * @param shift
     * @param from
     * @param to
     * @return
     */
    @POST
    @Path("/overtime/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean setOvertime(Shift shift,@PathParam("from") long from,@PathParam("to") long to){
        if(shift == null) throw new BadRequestException();
        return overtimeDB.setOvertime(shift,new Time(from), new Time(to));
    }

    /**
     *
     * @param startTime start time for available user
     * @param endTime end time for the available user
     * @return list of all users available in the given timespan
     */
    @GET
    @Path("/availability/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Available> getAvailableUsers(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime){
        if(startTime > endTime) throw new BadRequestException();
        ArrayList<Available> availableUsers =  userDb.getAvailableUsers(startTime, endTime);
        Map<Available, Available> map = new HashMap<>();
        for(Available user : availableUsers){
            map.put(user,user);
            System.out.println(user);
        }
        return map.values();
    }

    /**
     *
     * @param userId the id of the user
     * @param date date to be checked
     * @return true if user has a shift that date
     */
    @GET
    @Path("/absence/get/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Absence> getAbsenceFromUser(User user,@PathParam("startTime") long startTime,@PathParam("endTime") long endTime){
        ArrayList<Absence> absenceArrayList = absenceDb.getAbsenceForUser(user.getId(),new Timestamp(startTime),new Timestamp(endTime));
        Map<Absence, Absence> map = new HashMap<>();
        for(Absence absence : absenceArrayList){
            map.put(absence,absence);
        }
        return map.values();
    }

    @POST
    @Path("/absence/set/{userId}/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean setAbsence(@PathParam("userId") int userId, @PathParam("startTime") long startTime,@PathParam("endTime") long endTime ){
        if(userId < 0) throw new NotFoundException();
        if(startTime > endTime)throw new BadRequestException();
        return absenceDb.setAbsence(userId,new Timestamp(startTime),new Timestamp(endTime));
    }

    @Path("/available/{userId}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean userHasShift(@PathParam("userId") int userId, @PathParam("date") long date) {
        return userDb.userHasShift(userId, new java.sql.Date(date));
    }
    /*
    @GET
    @Path("/{userId}/timesheet/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getTimesheet(@PathParam("userId") int userId, @PathParam("startTime") long startTime, @PathParam("endTime") long endTime){
        Map<String,String> map = new HashMap<>();
        long hours = getHoursForPeriod(startTime,endTime,userId);

        map.put("Total tid: ", Long.toString(hours));
        //Sende ut liste over alle vakter gitt periode
        ArrayList<Shift> totalOvertime = new ArrayList<Shift>();

        ArrayList<Shift> totalShiftForPeriod = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime),userId);
        ArrayList<Absence> totalAbsence = absenceDb.getAbsenceForUser(userId, new Timestamp(startTime), new Timestamp(endTime));//Periode

        for(Shift shift : totalShiftForPeriod){
            Shift tempOvertime = overtimeDB.getOvertime(shift);
            map.put("Shift",shift.toString());
            if(tempOvertime.getStartTime().after(shift.getStartTime())){
                totalOvertime.add(tempOvertime);
                map.put("Overtid",tempOvertime.toString());
            }
        }

        return map.values();
    }*/
}
