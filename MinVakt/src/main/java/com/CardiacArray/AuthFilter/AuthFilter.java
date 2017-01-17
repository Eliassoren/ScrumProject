package com.CardiacArray.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.util.Map;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;

/**
 * Jersey HTTP Basic Auth filter
 * @author Deisss (LGPLv3)
 */

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    /**
     * Apply the filter : check input request, validate or not with user auth
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        System.out.println("AuthFilter");
        String authHeader = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Token not found");
            throw new NotAuthorizedException("Error");
        }
        String token = authHeader.substring("Bearer".length()).trim();
        try {
            UserDb userDb = new UserDb();
            User user = userDb.getUserByToken(token);
            if(user == null) {
                System.out.println("User not found");
                throw new NotAuthorizedException("Error");
            } else {
                System.out.println("User found");
                return;
            }
        } catch(Exception e) {
            throw new NotAuthorizedException("Error");
        }
    }
}