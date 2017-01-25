package com.CardiacArray.templates;

import com.CardiacArray.AuthFilter.Role;
import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.Mail.Mail;
import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.UserDb;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/site")
public class Templates {

    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;
    @Inject
    private ServletContext servletContext;
    private static final String HTMLPATH = "/WEB-INF/templates/";
    private ServletContextTemplateResolver templateResolver;
    private TemplateEngine templateEngine;
    private WebContext context;

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
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String login(@CookieParam("token") Cookie cookie) {
        if(cookie != null) {
            String token = cookie.getValue();
            UserDb userDb = new UserDb();
            User user = userDb.getUserByToken(token);
            if(user != null) {
                try {
                    URI uri = new URI("/MinVakt/calendar");
                    throw new RedirectionException(Response.Status.SEE_OTHER, uri);
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                    throw new NotAuthorizedException("Error");
                }
            }
        }
        return templateEngine.process("login", context);
    }
}
