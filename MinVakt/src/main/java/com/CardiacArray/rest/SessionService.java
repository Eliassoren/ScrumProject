/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.UserDb;

import java.security.SecureRandom;
import java.math.BigInteger;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.*;

/**
 *
 * @author Team 1
 */
@Path("/session")
public class SessionService {

    private UserDb userDb = new UserDb();

    public SessionService(UserDb userDb) throws Exception {
        this.userDb = userDb;
    }

    @Path("/login")
    @POST
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userDb.getUserByEmail(email);
        if(user != null) {
            if(user.getPassword().equals(password)) {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                user.setToken(token);
                userDb.updateUserToken(user);
                return Response.ok(token).build();
            } else return Response.status(Response.Status.UNAUTHORIZED).build();
        } else return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
