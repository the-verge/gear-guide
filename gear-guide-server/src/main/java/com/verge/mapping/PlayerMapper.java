package com.verge.mapping;


import com.verge.dto.PlayerInfo;
import com.verge.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerMapper {

    public PlayerInfo mapEntity(Player entity) {
        return new PlayerInfo(entity.getName(), entity.getAge());
    }

    public List<PlayerInfo> mapEntities(List<Player> entities) {
        List<PlayerInfo> dtos = new ArrayList<PlayerInfo>();
        for (Player entity : entities) {
            dtos.add(mapEntity(entity));
        }
        return dtos;
    }
}
