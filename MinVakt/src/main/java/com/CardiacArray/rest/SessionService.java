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

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 *
 * @author OddErik
 */
@Path("/session")
public class SessionService {
    private UserDb userDb = new UserDb();

    @Path("/login")
    @POST
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userDb.getUserByEmail(email);
        System.out.println("test");
        if(user != null) {
            System.out.println("test 2");
            if(user.getPassword().equals(password)) {
                System.out.println("test 3");
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                user.setToken(token);
                userDb.updateUserToken(user);
                return Response.ok(token).build();
            } else return Response.status(Response.Status.UNAUTHORIZED).build();
        } else return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
