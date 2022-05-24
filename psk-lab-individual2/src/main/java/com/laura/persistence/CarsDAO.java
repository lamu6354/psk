package com.laura.persistence;

import com.laura.entities.Car;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CarsDAO {

    @Setter
    @Inject
    private EntityManager entityManager;

    public void persist(Car car) {
        entityManager.persist(car);
    }

    public Car findOne(Integer id) {
        return entityManager.find(Car.class, id);
    }

    public Car update(Car car) {
        return entityManager.merge(car);
    }
}
