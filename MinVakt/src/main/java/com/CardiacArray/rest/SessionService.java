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
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Date;
import java.security.SecureRandom;
import java.math.BigInteger;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.*;

/**
 *
 * @author OddErik
 */
@Path("/session")
public class SessionService {

    private UserDb userDb = new UserDb();
    public SessionService () throws Exception {}

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
     public static void main(String[] args) throws Exception {
         DbManager dbManager = new DbManager();
     }
}
