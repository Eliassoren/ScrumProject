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
 * @author OddErik & Olof Andreas
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
        User userFound = userDb.getUser(email);
        if(userFound.getFirstName() == null || userFound.getLastName() == null) throw new NotFoundException();
        else return userFound;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(User user) {
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new BadRequestException();
        }
        boolean updateResponse = userDb.updateUser(user);
        if (!updateResponse) {
            throw new BadRequestException();
        } else {
            return updateResponse;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createUser(User user) {
        User checkUser = userDb.getUser(user.getEmail());
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || checkUser.getEmail() != null) {
            throw new BadRequestException();
        }
        else {
            userDb.createUser(user);
            return true;
        }
    }
}
