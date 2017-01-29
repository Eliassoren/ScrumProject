package com.CardiacArray.templateService.AuthFilter;

import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.db.UserDb;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
/**
 *
 * Authenticates user trying to access the HTML pages. It looks for the token in the AUTHORIZATION entry of the HTTP header.
 * If the token is not found in the HTTP header or if a user with the received token is not found in the database, HTTP error code 401 is returned.
 *
 */
@SecuredTpl
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        try {
            URI uri = new URI("/MinVakt/site/");
            System.out.println("AuthenticationFilter 2");
            if(containerRequest.getCookies().get("token") == null) {
                throw new RedirectionException(Response.Status.SEE_OTHER, uri);
            }
            String token = containerRequest.getCookies().get("token").getValue();
            UserDb userDb = new UserDb();
            User user = userDb.getUserByToken(token);
            if (user == null) {
                throw new RedirectionException(Response.Status.SEE_OTHER, uri);
            } else {
                LocalDateTime expiredTime = user.getExpired().toLocalDateTime();
                if (expiredTime.isBefore(LocalDateTime.now()) || !user.isActive()) {
                    user.setToken(null);
                    user.setExpired(null);
                    userDb.updateUserToken(user);
                    throw new RedirectionException(Response.Status.SEE_OTHER, uri);
                }
                return;
            }
        } catch(URISyntaxException e) {
                e.printStackTrace();
        }
    }
}