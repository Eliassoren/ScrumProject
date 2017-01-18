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
import java.time.LocalDateTime;
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
        String authHeader = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Error");
        }
        String token = authHeader.substring("Bearer".length()).trim();
        try {
            UserDb userDb = new UserDb();
            User user = userDb.getUserByToken(token);
            if(user == null) {
                throw new NotAuthorizedException("Error");
            } else {
                LocalDateTime expiredTime = user.getExpired().toLocalDateTime();
                if(expiredTime.isBefore(LocalDateTime.now())) {
                    user.setToken(null);
                    user.setExpired(null);
                    userDb.updateUserToken(user);
                    throw new NotAuthorizedException("Error");
                }
                return;
            }
        } catch(Exception e) {
            throw new NotAuthorizedException("Error");
        }
    }
}