package com.verge.infrastructure;

import org.flywaydb.core.Flyway;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;


public class PersistenceSetup {

    @Inject
    EntityManager em;

    private static final String CONNECTION_URL_PROPERTY = "javax.persistence.jdbc.url";
    private static final String CONNECTION_USER_PROPERTY = "javax.persistence.jdbc.user";
    private static final String CONNECTION_PASSWORD_PROPERTY = "javax.persistence.jdbc.password";

    public void setUpPersistence() {
        String url = (String) getProperty(CONNECTION_URL_PROPERTY);
        String user = (String) getProperty(CONNECTION_USER_PROPERTY);
        String password = (String) getProperty(CONNECTION_PASSWORD_PROPERTY);

        Flyway flyway = new Flyway();
        flyway.setDataSource(url, user, "");
        flyway.migrate();
    }

    private Object getProperty(String propertyName) {
        Map<String, Object> persistenceProperties = em.getEntityManagerFactory().getProperties();
        return persistenceProperties.get(propertyName);
    }
}
