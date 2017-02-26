package com.verge.dao.impl;


import com.verge.entity.Player;

import javax.persistence.Query;
import java.util.List;

public class PlayerRepository extends BaseRepository implements com.verge.dao.PlayerDao {

    public List<Player> getPlayers() {
        Query query = em.createQuery("from Player");
        return query.getResultList();
    }
}
