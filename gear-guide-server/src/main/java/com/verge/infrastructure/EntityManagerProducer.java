package com.verge.infrastructure;


import javax.ejb.Singleton;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

@Singleton
public class EntityManagerProducer {

    private PropertiesHelper propertiesHelper;

    private EntityManagerFactory emf;

    public EntityManagerProducer() {
        // EJB requirement
    }

    @Inject
    public EntityManagerProducer(PropertiesHelper propertiesHelper) {
        this.propertiesHelper = propertiesHelper;
        Map<String, String> persistenceProperties = propertiesHelper.getPersistenceUnitProperties();
        String persistenceUnitName = propertiesHelper.getPersistenceUnitName();
        emf = Persistence.createEntityManagerFactory(persistenceUnitName, persistenceProperties);
    }

    @Produces
    @RequestScoped
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

}
