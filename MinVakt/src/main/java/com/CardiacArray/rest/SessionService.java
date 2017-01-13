/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.rest;

import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.SessionDb;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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
    @Produces("application/json")
    public Session login(@FormParam("email") String email, @FormParam("password")String password) {
        Session session = new Session();
        if(sessionDb.login(email, password) > -1) {

        } else {
            throw new NotAuthorizedException("Feil brukernavn eller passord");
        }
        return session;
    }

    
     public static void main(String[] args) {
        SessionService sc = new SessionService();
        
        sc.login("epost@internett.no", "123");
    }
}
