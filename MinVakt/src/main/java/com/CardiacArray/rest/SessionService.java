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
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private PasswordUtil passwordUtil = new PasswordUtil();

    public SessionService() {
    }

    public SessionService(UserDb userDb) throws Exception {
        this.userDb = userDb;
    }

    @Path("/login")
    @POST
    public Response login(@FormParam("login_email") String email, @FormParam("login_password") String password) {
        User user = userDb.getUserByEmail(email);
        if(user != null) {
            if(passwordUtil.verifyPassword(password, user.getFirstName(), user.getPassword())) {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                LocalDateTime expiredTime = LocalDateTime.now().plusWeeks(1);
                Timestamp expired = Timestamp.valueOf(expiredTime);
                user.setToken(token);
                user.setExpired(expired);
                userDb.updateUserToken(user);
                return Response.ok(token).build();
            } else return Response.status(Response.Status.UNAUTHORIZED).build();
        } else return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Secured
    @GET
    @Path("/checktoken")
    public Response checkToken() {
        return Response.ok().build();
    }

     public static void main(String[] args) throws Exception {
         DbManager dbManager = new DbManager();
     }
}
