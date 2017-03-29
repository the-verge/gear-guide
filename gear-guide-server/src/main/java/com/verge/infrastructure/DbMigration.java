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
    private static final String SCHEMA_MIGRATION_PATH = "db/migration";

    public void migrateDb() {
        Map<String, String> properties = propertiesHelper.getPersistenceUnitProperties();

        String url = properties.get(CONNECTION_URL_PROPERTY);
        String user = properties.get(CONNECTION_USER_PROPERTY);
        String password = properties.get(CONNECTION_PASSWORD_PROPERTY);

        Flyway flyway = new Flyway();
        flyway.setLocations(SCHEMA_MIGRATION_PATH, getDataMigrationPath());
        flyway.setDataSource(url, user, password);
        flyway.migrate();
    }

    private String getDataMigrationPath() {
        return "db/" + propertiesHelper.getEnv();
    }

}
