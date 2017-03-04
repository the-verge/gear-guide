package com.verge.infrastructure;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Bootstrap {

    @Inject
    private PersistenceSetup persistenceSetup;

    @PostConstruct
    public void init() {
        persistenceSetup.setUpPersistence();
    }

}
