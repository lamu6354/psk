package com.laura.persistence;

import com.laura.entities.Autoservice;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class AutoservicesDAO {

    @Setter
    @Inject
    private EntityManager entityManager;

    public List<Autoservice> loadAll() {
        return entityManager.createNamedQuery("Autoservice.findAll", Autoservice.class).getResultList();
    }

    public void persist(Autoservice autoservice) {
        entityManager.persist(autoservice);
    }

    public Autoservice findOne(int id) {
        return entityManager.find(Autoservice.class, id);
    }

    public void update(Autoservice autoservice) {
        entityManager.merge(autoservice);
    }

    public Autoservice findByName(String autoserviceName) {
        try {
            Query query = entityManager.createNamedQuery("Autoservice.findByName");
            query.setParameter("name", autoserviceName);
            return (Autoservice) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
