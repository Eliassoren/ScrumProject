package com.CardiacArray.rest;

import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.ShiftDb;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.db.DbManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Team 1 
 */

@Secured
@Path("/users")
public class UserService {
    private UserDb userDb = new UserDb();
    private ShiftDb shiftDb = new ShiftDb();
    private OvertimeDB overtimeDB = new OvertimeDB();
    private PasswordUtil passwordUtil = new PasswordUtil();

    public UserService(UserDb userDb) throws Exception {
        this.userDb = userDb;
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

    /*
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {
        User userFound = userDb.getUserByEmail(user.getEmail());
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
        else userDb.deleteUser(user);
    }*/

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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public long findHoursForPeriod(long startTime, long endTime, int userId){
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
    @Produces(MediaType.APPLICATION_JSON)
    public String [][] findOvertimeForPeriod(Shift shift, long startTime, long endTime){
        ArrayList<Shift> shifts = shiftDb.getShiftsForPeriod(new Date(startTime),new Date(endTime),shift.getUserId());
        String[][]overtimeArray = new String[shifts.size()][2];
        for(int i = 0; i < shifts.size(); i++){
            Shift temp =  overtimeDB.getOvertime(shifts.get(i));
            if(!temp.equals(shifts.get(i))){
                long overtimeInHours = temp.getEndTime().getTime()-temp.getStartTime().getTime();
                String start = temp.getStartTime().toString();
                String end = temp.getEndTime().toString();
                overtimeArray[i][0] = start+end;
                overtimeArray[i][1] = Long.toString(overtimeInHours);
            }
        }
       return overtimeArray;
    }

    public void setOvertime(){

    }
}
