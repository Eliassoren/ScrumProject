package com.CardiacArray.restService.AuthFilter;

import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.db.UserDb;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

/**
 *
 * Authenticates user trying to access the rest service. It looks for the token in the AUTHORIZATION entry of the HTTP header.
 * If the token is not found in the HTTP header or if a user with the received token is not found in the database, HTTP error code 401 is returned.
 *
 */

@SecuredRest
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        System.out.println("AuthenticationFilter");
        String authHeader = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Error");
        }
        String token = authHeader.substring("Bearer".length()).trim();
        UserDb userDb = new UserDb();
        User user = userDb.getUserByToken(token);
        if(user == null) {
            throw new NotAuthorizedException("Error");
        } else {
            LocalDateTime expiredTime = user.getExpired().toLocalDateTime();
            if (expiredTime.isBefore(LocalDateTime.now())) {
                user.setToken(null);
                user.setExpired(null);
                userDb.updateUserToken(user);
                throw new NotAuthorizedException("Error");
            }
            return;
        }
    }
}