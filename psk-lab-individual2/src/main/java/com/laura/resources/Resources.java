package com.laura.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static javax.persistence.SynchronizationType.SYNCHRONIZED;

@ApplicationScoped
public class Resources {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Produces
    @RequestScoped
    private EntityManager getEntityManager() {
        return factory.createEntityManager(SYNCHRONIZED);
    }

    private void closeEntityManager(@Disposes @Default EntityManager manager) {
        manager.close();
    }
}
