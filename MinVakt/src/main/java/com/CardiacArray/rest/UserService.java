/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.Session;
import com.CardiacArray.data.User;
import com.CardiacArray.db.SessionDb;
import com.CardiacArray.db.UserDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author OddErik
 */
public class UserService {
    private Connection connection =
    private UserDb userDb = new UserDb();
    
    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return userDb.getUser;
    }
    
    @PUT
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(User user) {
        userDb.updateUser(user);
    }
    
    @DELETE
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(User user) {
        userDb.deleteUser(user);
    }
    
}
