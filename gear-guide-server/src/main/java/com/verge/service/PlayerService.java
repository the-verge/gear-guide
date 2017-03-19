package com.verge.service;


import com.verge.repository.PlayerRepository;
import com.verge.dto.PlayerInfo;
import com.verge.entity.Player;
import com.verge.mapping.PlayerMapper;

import javax.inject.Inject;
import java.util.List;

public class PlayerService {

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerMapper playerMapper;

    public List<PlayerInfo> getPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerInfo> dtos = playerMapper.entitiesToDtos(players);
        return dtos;
    }

    public PlayerInfo getPlayer(Long id) {
        Player player = playerRepository.findBy(id);
        return playerMapper.entityToDto(player);
    }

    public List<PlayerInfo> findByName(String name) {
        List<Player> players = playerRepository.findByName(name);
        return playerMapper.entitiesToDtos(players);
    }
}
