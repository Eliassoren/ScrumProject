package com.CardiacArray.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Vegard on 10/01/2017.
 */
@Path("/restshit/")
public class main {
    @GET
    @Produces("text/html")
    public String getHello(){
        return "Hello!";
    }
}
