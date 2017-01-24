package com.CardiacArray.rest;

import com.CardiacArray.AuthFilter.Role;
import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.OvertimeDb;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.db.DbManager;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
    private PasswordUtil passwordUtil = new PasswordUtil();

    public UserService(UserDb userDb) throws Exception {
        this.userDb = userDb;
    }

    public UserService() throws Exception {
    }

    /**
     *
     * @param email email of the user
     * @return user object
     */
     @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        User userFound = userDb.getUserByEmail(email);
        if(userFound.getFirstName() == null || userFound.getLastName() == null) throw new NotFoundException();
        else return userFound;
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
    @Path("/available/{userId}/{date}/{start}/{end}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setUserAvailable(@PathParam("userId") int userId, @PathParam("date") long date,
                                     @PathParam("start") long start, @PathParam("end") long end) {
        User user = userDb.getUserByEmail(userId);
        Date dateAvail = new Date(date);
        Date startAvail = new Date(start);
        Date endAvail = new Date(end);
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || !user.isValidEmail(user.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userDb.setUserAvailable(userId, dateAvail, startAvail, endAvail);
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
     * @param startTime starttime for available user
     * @param endTime endtime for the available user
     * @return list of all users avaiable in the given timespan
     */
    @GET
    @Path("/availability/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getAvailableUsers(@PathParam("startTime") long startTime, @PathParam("endTime") long endTime){
        if(new Date(startTime).after(new Date(endTime))) throw new BadRequestException();
        ArrayList<User> avaiableUsers =  userDb.getAvailableUsers(startTime, endTime);
        Map<User, User> map = new HashMap<>();
        for(User user : avaiableUsers){
            map.put(user,user);
        }
        return map.values();
    }

    @GET
    @Path("/timesheet/{userId}/{startTime}/{endTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTimesheet(@PathParam("userId") int userId, @PathParam("startTime") long startTime, @PathParam("endTime") long endTime){
        long hours = getHoursForPeriod(startTime,endTime,userId);


    }

}
