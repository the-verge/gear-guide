package com.verge.infrastructure;

import org.flywaydb.core.Flyway;

import javax.inject.Inject;
import java.util.Map;


public class DbMigration {

    @Inject
    private PropertiesHelper propertiesHelper;

    private static final String CONNECTION_URL_PROPERTY = "javax.persistence.jdbc.url";
    private static final String CONNECTION_USER_PROPERTY = "javax.persistence.jdbc.user";
    private static final String CONNECTION_PASSWORD_PROPERTY = "javax.persistence.jdbc.password";

    public void migrateDb() {
        Map<String, String> properties = propertiesHelper.getPersistenceUnitProperties();

        String url = properties.get(CONNECTION_URL_PROPERTY);
        String user = properties.get(CONNECTION_USER_PROPERTY);
        String password = properties.get(CONNECTION_PASSWORD_PROPERTY);

        Flyway flyway = new Flyway();
        flyway.setDataSource(url, user, password);
        flyway.migrate();
    }

}
