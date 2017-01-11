/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.Session;
import com.CardiacArray.data.User;
import com.CardiacArray.db.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author OddErik
 */
@Path("/session")
public class SessionService {
    
    SessionDb sessionDb;
    @Context private HttpServletRequest request;
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    
    public Session login(String email, String password) {
        DbManager dbManager = new DbManager();
        sessionDb = new SessionDb(dbManager.connection);
        Session session = new Session();
        
        if(sessionDb.login(email, password) > -1) {
            session.setLoginDate(new Date());
            session.setEmail(email);
            request.getSession().invalidate();
            request.setAttribute("session",session);
            System.out.println("Funker");
        } else {
            request.getSession().invalidate();
            throw new NotAuthorizedException("Feil brukernavn eller passord");
        }

        return session;
    }
    
     public static void main(String[] args) {
        SessionService sc = new SessionService();
        
        sc.login("epost@internett.no", "123");
    }
}
