package com.verge.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("players")
@Produces(MediaType.APPLICATION_JSON)
public interface PlayerResource {

    @GET
    Response getAllPlayers();

    @Path("/{id}")
    @GET
    Response getPlayer(@PathParam("id") Long id);

    @Path("/query")
    @GET
    Response getPlayerByName(@QueryParam("name") String name);
}
