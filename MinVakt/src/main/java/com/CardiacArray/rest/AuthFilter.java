package com.CardiacArray.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;
import java.util.Map;
import com.CardiacArray.db.SessionDb;

/**
 * Jersey HTTP Basic Auth filter
 * @author Deisss (LGPLv3)
 */

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    /**
     * Apply the filter : check input request, validate or not with user auth
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath(true);
        Map<String, Cookie> cookies = containerRequest.getCookies();
        /*if(method == "POST" && path =="session/login") {
            containerRequest.set
        }
        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");

        //If the user does not have the right (does not provide any HTTP Basic Auth)
        if(auth == null){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
/*
        //lap : loginAndPassword
        String[] lap = BasicAuth.decode(auth);

        //If login or password fail
        if(lap == null || lap.length != 2){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
        User authentificationResult =  AuthentificationThirdParty.authentification(lap[0], lap[1]);

        //Our system refuse login and password
        if(authentificationResult == null){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
*/
        return;
    }
}