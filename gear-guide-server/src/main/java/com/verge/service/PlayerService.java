package com.verge.service;


import com.verge.dao.PlayerDao;
import com.verge.dto.PlayerInfo;
import com.verge.entity.Player;
import com.verge.mapping.PlayerMapper;

import javax.inject.Inject;
import java.util.List;

public class PlayerService {

    @Inject
    private PlayerDao playerDao;

    @Inject
    private PlayerMapper playerMapper;

    public List<PlayerInfo> getPlayers() {
        List<Player> players = playerDao.getPlayers();
        List<PlayerInfo> dtos = playerMapper.mapEntities(players);
        return dtos;
    }
}
