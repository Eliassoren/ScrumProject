package com.CardiacArray.rest;

import com.CardiacArray.data.Shift;
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
    public void updateShift(Shift shift) {
        shiftDb.updateShift(shift);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteShift(Shift shift) {
        shiftDb.deleteShift(shift);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void addShift(Shift shift) {
        shiftDb.addShift(shift);
    }
}
