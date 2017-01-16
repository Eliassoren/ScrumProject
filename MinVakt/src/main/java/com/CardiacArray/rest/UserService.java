/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.User;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.UserDb;
import java.sql.Connection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author OddErik
 */

@Path("/users")
public class UserService {
    private UserDb userDb = new UserDb();

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        return userDb.getUser(email);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User user) {
        userDb.updateUser(user);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(User user) {
        userDb.deleteUser(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createUser(User user) {
        userDb.createUser(user);
    }
}
