package com.verge.data.repository.deltaspike;

import com.verge.entity.Player;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;


@Repository(forEntity = Player.class)
public interface PlayerRepository extends EntityRepository<Player, Long> {

    @Query("select p from Player p where p.name like :name")
    List<Player> findByName(@QueryParam("name") String name);

}
