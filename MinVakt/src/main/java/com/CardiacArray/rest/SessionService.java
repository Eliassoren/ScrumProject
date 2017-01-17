/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


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
    @Produces("application/json")
    public Session login(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userDb.getUser(email);
        if(user.getEmail() != null) {
            if (user.getPassword().equals(password)) {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                System.out.println(token);/*
            user.setToken(token);
            userDb.updateUser(user);*/
            } else throw new NotFoundException();
        } else throw new NotFoundException();
        return new Session();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    /*public Response login2(@PathParam("email") String email, @PathParam("password") String password) throws URISyntaxException {
            if(sessionDb1.login(email, password) == -1){
                HttpSession session = request.getSession();
                session.setAttribute("session", session);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        return Response.ok("token").build();
    }
    */
     public static void main(String[] args) throws Exception {
         DbManager dbManager = new DbManager();
     }
}
3