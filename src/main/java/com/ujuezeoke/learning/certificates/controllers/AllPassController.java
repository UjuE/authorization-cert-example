package com.ujuezeoke.learning.certificates.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Obianuju Ezeoke on 09/04/2017.
 */
@Path("")
public class AllPassController {

    @GET
    @Path("/access")
    public Response success(){
        return Response.ok("Okay").build();
    }
}
