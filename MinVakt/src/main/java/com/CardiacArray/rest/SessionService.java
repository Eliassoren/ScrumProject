/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.UserDb;

import java.security.SecureRandom;
import java.math.BigInteger;
import javax.ws.rs.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Team 1
 */
@Path("/session")
public class SessionService {

    private UserDb userDb = new UserDb();

    public SessionService (userDb) throws Exception {
        this.userDb = userDb;
    }

    @Path("/login")
    @POST
    @Produces("application/json")
    public Session login(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userDb.getUser(email);
        if(user.getEmail() != null) {
            if (user.getPassword().equals(password)) {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
            } else throw new NotFoundException();
        } else throw new NotFoundException();
        return new Session();
    }

}
