package com.CardiacArray.rest;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.db.DbManager;
import java.sql.Connection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Odd Erik
 */

@Path("/users")
public class UserService {
    private UserDb userDb = new UserDb();

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
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
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

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {
        User userFound = userDb.getUserByEmail(user.getEmail());
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
        else userDb.deleteUser(user);
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
            userDb.createUser(user);
            return true;
        }
        else throw new BadRequestException();

    }
}
