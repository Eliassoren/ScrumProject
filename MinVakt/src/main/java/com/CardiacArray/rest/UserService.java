/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author OddErik
 */

@Path("/users")
public class UserService {
    private UserDb userDb = new UserDb();

    public UserService(UserDb userDb) throws Exception {
        this.userDb = userDb;
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        User userFound = userDb.getUserByEmail(email);
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
        else return userFound;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(User user) {
        boolean updateResponse = userDb.updateUser(user);
        if(!updateResponse) throw new BadRequestException();
        else return updateResponse;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {
        User userFound = userDb.getUserByEmail(user.getEmail());
        if(userFound.getFirstName() == null && userFound.getLastName() == null) throw new NotFoundException();
        else userDb.deleteUser(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createUser(User user) {
        User checkUser = getUserByEmail(user.getEmail());
        if(checkUser.getFirstName() == null && checkUser.getLastName() == null){
            userDb.createUser(user);
            return true;
        }
        else throw new BadRequestException();

    }
}
