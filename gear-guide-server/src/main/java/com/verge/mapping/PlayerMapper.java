package com.verge.mapping;


import com.verge.dto.PlayerInfo;
import com.verge.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerMapper implements Mapper<Player, PlayerInfo> {

    @Override
    public PlayerInfo entityToDto(Player entity) {
        return new PlayerInfo(entity.getId(), entity.getName(), entity.getAge());
    }

    @Override
    public List<PlayerInfo> entitiesToDtos(Collection<Player> entities) {
        return entities.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toList());
    }

    @Override
    public Player dtoToEntity(PlayerInfo dto) {
        return null;
    }

    @Override
    public List<Player> dtosToEntities(Collection<PlayerInfo> dtos) {
        return null;
    }

}
