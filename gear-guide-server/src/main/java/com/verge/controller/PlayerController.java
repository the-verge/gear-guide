package com.verge.controller;


import com.verge.service.PlayerService;
import com.verge.utils.Responses;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("players")
public class PlayerController {

    @Inject
    private PlayerService playerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {
        return Responses.ok(playerService.getPlayers());
    }

}
