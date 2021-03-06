package com.CardiacArray.restService.AuthFilter;

import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.db.UserDb;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Checks if a user is authorized to use the specified rest service. MinVakt separates between regular users and admins. This filter will attempt to
 * get the user from the database and compare its set role with that required by the requested rest service.
 * If the user is not authorized, HTTP error 403 is returned.
 *
 */

@SecuredRest
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    private ContainerRequestContext containerRequest;

    /**
     *
     * @param containerRequest The request from user session
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        System.out.println("AuthorizationFilter");
        this.containerRequest = containerRequest;
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        try {
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles);
            } else {
                checkPermissions(methodRoles);
            }
        } catch (Exception e) {
            containerRequest.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            SecuredRest secured = annotatedElement.getAnnotation(SecuredRest.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles) throws Exception {
        String authHeader = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ForbiddenException("Error");
        }
        String token = authHeader.substring("Bearer".length()).trim();
        UserDb userDb = new UserDb();
        User user = userDb.getUserByToken(token);
        if(user == null) {
            throw new ForbiddenException("Error");
        } else {
            LocalDateTime expiredTime = user.getExpired().toLocalDateTime();
            if (expiredTime.isBefore(LocalDateTime.now()) || !user.isActive()) {
                user.setToken(null);
                user.setExpired(null);
                userDb.updateUserToken(user);
                throw new ForbiddenException("Error");
            } else if(!allowedRoles.contains(user.getRole())) {
                throw new ForbiddenException("Error");
            }
            return;
        }
    }
}