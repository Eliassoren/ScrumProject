/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.SessionDb;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

@Path("session")
public class SessionService {
    
    SessionDb sessionDb;
    private DbManager dbManager = new DbManager();
    private SessionDb sessionDb1;

    public SessionService (Connection connection) throws Exception {
        sessionDb1 = new SessionDb(connection);
    }

    @Context private HttpServletRequest request;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Session login(String email, String password, HttpSession httpSession) {
        try {
            dbManager = new DbManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionDb = new SessionDb(dbManager.connection);
        Session session = new Session();

        if(sessionDb1.login(email, password) > -1) {
            session.setLoginDate(new Date());
            session.setEmail(email);
            session.setLoggedIn(true);
            httpSession.setAttribute("websession", session);

        } else {
            throw new NotAuthorizedException("Feil brukernavn eller passord");
        }

        return session;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login2(@PathParam("email") String email, @PathParam("password") String password) throws URISyntaxException {
            if(sessionDb1.login(email, password) == -1){
                HttpSession session = request.getSession();
                session.setAttribute("session", session);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        return Response.ok("token").build();
    }
    
     public static void main(String[] args) throws Exception {
        DbManager dbManager = new DbManager();

        SessionService sc = new SessionService(dbManager.connection);
        
    }
}
