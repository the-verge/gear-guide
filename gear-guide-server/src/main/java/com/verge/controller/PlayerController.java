package com.verge.controller;


import com.verge.resource.PlayerResource;
import com.verge.service.PlayerService;
import com.verge.utils.Responses;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class PlayerController implements PlayerResource {

    @Inject
    private PlayerService playerService;

    public Response getAllPlayers() {
        return Responses.ok(playerService.getPlayers());
    }

    public Response getPlayer(Long id) {
        return Responses.ok(playerService.getPlayer(id));
    }

    public Response getPlayerByName(String name) {
        return Responses.ok(playerService.findByName(name));
    }

}
