package com.verge.test.common;

import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public class BaseITest {

    protected static File[] resolveDependencies() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();

        return files;
    }

    protected static File getTestBeansXml() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("test-beans.xml").getFile());
        return file;
    }
}
