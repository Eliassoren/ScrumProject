package com.CardiacArray.rest;

import com.CardiacArray.data.Shift;
import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;

/**
 * Created by OddErik on 12.01.2017.
 */

@Path("/shifts")
public class ShiftService {

    private ShiftDb shiftDb;

    public ShiftService(Connection con) {
        shiftDb = new ShiftDb(con);
    }

    @GET
    @Path("/{shiftId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Shift getShift(@PathParam("id") int id) {
        return shiftDb.getShift(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateShift(int id) {
        shiftDb.updateShift(id);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteShift(int id) {
        shiftDb.deleteShift(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void addShift(int id) {
        shiftDb.addShift(id);
    }


}
