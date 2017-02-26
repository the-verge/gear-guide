package com.verge.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseRepository {

    @PersistenceContext(unitName = "gear_guide")
    protected EntityManager em;
}
