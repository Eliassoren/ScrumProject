package com.CardiacArray.templateService.AuthFilter;

import com.CardiacArray.restService.AuthFilter.Role;
import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.db.UserDb;

import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.RedirectionException;
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
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SecuredTpl
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    private ContainerRequestContext containerRequest;

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        System.out.println("AuthorizationFilter 2");
        this.containerRequest = containerRequest;
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
        try {
            if (methodRoles.isEmpty()) {
                System.out.println("Authoriz: checking for class permissions");
                checkPermissions(classRoles);
            } else {
                System.out.println("Authoriz: checking for method permissions");
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
            SecuredTpl secured = annotatedElement.getAnnotation(SecuredTpl.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles) throws Exception {
        URI uri = new URI("/MinVakt/site/");
        System.out.println("AuthenticationFilter 2");
        if(containerRequest.getCookies().get("token") == null) {
            System.out.println("Authoriz: Token not found");
            throw new RedirectionException(Response.Status.SEE_OTHER, uri);
        }
        String token = containerRequest.getCookies().get("token").getValue();
        UserDb userDb = new UserDb();
        User user = userDb.getUserByToken(token);
        if(user == null) {
            System.out.println("Authoriz: User not found");
            throw new RedirectionException(Response.Status.SEE_OTHER, uri);
        } else {
            System.out.println("Authoriz: User found");
            LocalDateTime expiredTime = user.getExpired().toLocalDateTime();
            if (expiredTime.isBefore(LocalDateTime.now())) {
                user.setToken(null);
                user.setExpired(null);
                userDb.updateUserToken(user);
                throw new RedirectionException(Response.Status.SEE_OTHER, uri);
            } else if(!allowedRoles.contains(user.getRole())) {
                throw new RedirectionException(Response.Status.SEE_OTHER, uri);
            }
            System.out.println("Authoriz: User has access");
            return;
        }
    }
}