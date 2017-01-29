package com.CardiacArray.templateService;
import com.CardiacArray.restService.data.*;
import com.CardiacArray.restService.db.UserDb;
import com.CardiacArray.restService.AuthFilter.Role;

import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.*;

import com.CardiacArray.templateService.AuthFilter.SecuredTpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("")
public class Templates {

    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;
    @Inject
    private ServletContext servletContext;
    @Context
    private UriInfo uriInfo;
    private @CookieParam("token") Cookie cookie;
    private ServletContextTemplateResolver templateResolver;
    private TemplateEngine templateEngine;
    private WebContext context;
    private UserDb userDb = new UserDb();
    private User user;

    @PostConstruct
    public void postConstruct() {
        templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        context = new WebContext(request, response, servletContext);
        templateEngine.setTemplateResolver(templateResolver);
        if(cookie != null) {
            user = userDb.getUserByToken(cookie.getValue());
            context.setVariable("user", user);
        }
        context.setVariable("header", "header");
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String login() {
        if(user != null) {
            if(user.isAdmin()) {
                try {
                    URI uri = new URI("/MinVakt/site/admin");
                    throw new RedirectionException(Response.Status.SEE_OTHER, uri);
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                    throw new NotAuthorizedException("Error");
                }
            } else {
                try {
                    URI uri = new URI("/MinVakt/site/calendar");
                    throw new RedirectionException(Response.Status.SEE_OTHER, uri);
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                    throw new NotAuthorizedException("Error");
                }
            }

        }
        return templateEngine.process("login", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/calendar")
    @Produces(MediaType.TEXT_HTML)
    public String calendar() {

        context.setVariable("header", "header");
        return templateEngine.process("calendar", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public String admin() {
        return templateEngine.process("admin", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/admin/shift")
    @Produces(MediaType.TEXT_HTML)
    public String adminShift() {
        return templateEngine.process("admin-shift", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/admin/shift-accept")
    @Produces(MediaType.TEXT_HTML)
    public String adminShiftAccept() {
        return templateEngine.process("admin-shift-accept", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/admin/userlist")
    @Produces(MediaType.TEXT_HTML)
    public String userList() {
        return templateEngine.process("admin-userlist", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/admin/overtime")
    @Produces(MediaType.TEXT_HTML)
    public String overtime() {
        return templateEngine.process("admin-overtime", context);
    }

    @SecuredTpl({Role.USER, Role.ADMIN})
    @GET
    @Path("/availability")
    @Produces(MediaType.TEXT_HTML)
    public String availability() {
        return templateEngine.process("availability", context);
    }

}
