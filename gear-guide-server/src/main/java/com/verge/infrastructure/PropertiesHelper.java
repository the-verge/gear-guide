package com.verge.infrastructure;

import com.google.common.collect.Maps;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {

    private static final String PERSISTENCE_UNIT_NAME_KEY = "persistenceUnitName";

    private String propertiesFilePathTemplate = "properties/persistence_properties_{0}.properties";

    private ProjectStage projectStage;

    private Map<String, String> persistenceProperties;

    @Inject
    public PropertiesHelper(ProjectStage projectStage) {
        this.projectStage = projectStage;
        persistenceProperties = Maps.fromProperties(getProperties());
    }

    public Map<String, String> getPersistenceUnitProperties() {
        return persistenceProperties;
    }

    public String getPersistenceUnitName() {
        return persistenceProperties.get(PERSISTENCE_UNIT_NAME_KEY);
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getInputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private InputStream getInputStream() {
        String filepath = getFilePath();
        return getClass().getClassLoader().getResourceAsStream(filepath);
    }

    private String getFilePath() {
        return MessageFormat.format(propertiesFilePathTemplate, getEnv());
    }

    private String getEnv() {
        return projectStage.toString();
    }
}
