package com.laura.persistence;

import com.laura.entities.Mechanic;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class MechanicsDAO {

    @Setter
    @Inject
    private EntityManager entityManager;

    public void persist(Mechanic mechanic) {
        entityManager.persist(mechanic);
    }

    public List<Mechanic> findAll() {
        return entityManager.createNamedQuery("Mechanic.findAll", Mechanic.class).getResultList();
    }

    public Mechanic findByName(String name) {
        try {
            Query query = entityManager.createNamedQuery("Mechanic.findByName");
            query.setParameter("name", name);
            return (Mechanic) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void update(Mechanic mechanic) {
        entityManager.merge(mechanic);
    }
}
