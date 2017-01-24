package com.CardiacArray.templates;

import com.CardiacArray.AuthFilter.Role;
import com.CardiacArray.AuthFilter.Secured;
import com.CardiacArray.Mail.Mail;
import com.CardiacArray.data.*;
import com.CardiacArray.db.DbManager;
import com.CardiacArray.db.UserDb;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

@Path("/")
public class Templates {

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private ServletContext serverContext;

    private static TemplateEngine templateEngine;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(serverContext);
        templateResolver.setTemplateMode("HTML5");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(request, response, serverContext);

        context.setVariable("test", "Lorem Ipsum");

        return templateEngine.process("/test.html", context);
    }

}
