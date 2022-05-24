package com.laura.usecases;

import com.laura.entities.Car;
import com.laura.entities.Mechanic;
import com.laura.interceptors.Logger;
import com.laura.entities.Autoservice;
import com.laura.persistence.AutoservicesDAO;
import com.laura.persistence.MechanicsDAO;
import com.laura.persistence.CarsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static javax.faces.context.FacesContext.getCurrentInstance;

@Model
public class CarsForAutoservice implements Serializable {

    @Inject
    private AutoservicesDAO autoservicesDAO;

    @Inject
    private CarsDAO carsDAO;

    @Inject
    private MechanicsDAO mechanicsDAO;

    @Getter
    @Setter
    private Autoservice autoservice;

    @Getter
    @Setter
    private Car carToCreate = new Car();

    @Getter
    @Setter
    private Mechanic mechanicToAdd = new Mechanic();

    @Getter
    @Setter
    private List<SelectItem> allMechanics;

    @Transactional
    @Logger
    public String createCar() {
        carToCreate.setAutoservice(this.autoservice);
        carsDAO.persist(carToCreate);
        return "cars?faces-redirect=true&autoserviceId=" + this.autoservice.getId();
    }

    @Transactional
    @Logger
    public String createMechanicFromDropdown() {
        List<Autoservice> allMechanicAutoservices = mechanicToAdd.getAutoservices();
        allMechanicAutoservices.add(this.autoservice);
        mechanicToAdd.setAutoservices(allMechanicAutoservices);
        mechanicsDAO.update(mechanicToAdd);
        return "index?faces-redirect=true";
    }

    @Transactional
    @Logger
    public String createMechanic() {
        Mechanic foundMechanic = mechanicsDAO.findByName(mechanicToAdd.getName());
        if (isNull(foundMechanic)) {
            handleNewMechanic();
        } else {
            handleExistingMechanic(foundMechanic);
        }

        return "index?faces-redirect=true";
    }

    private void handleExistingMechanic(Mechanic foundMechanic) {
        mechanicToAdd.setId(foundMechanic.getId());
        List<Autoservice> allMechanicAutoservices = foundMechanic.getAutoservices();
        allMechanicAutoservices.add(this.autoservice);
        mechanicToAdd.setAutoservices(allMechanicAutoservices);
        mechanicsDAO.update(mechanicToAdd);
    }

    private void handleNewMechanic() {
        List<Autoservice> allMechanicAutoservices = new ArrayList<>();
        allMechanicAutoservices.add(this.autoservice);
        mechanicToAdd.setAutoservices(allMechanicAutoservices);
        mechanicsDAO.persist(mechanicToAdd);
    }

    @PostConstruct
    @Logger
    private void init() {
        Map<String, String > requestParams = getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();


        int courseId = Integer.parseInt(requestParams.get("autoserviceId"));
        this.autoservice = autoservicesDAO.findOne(courseId);
        this.allMechanics = new ArrayList<>();
        List<Mechanic> availableMechanics = mechanicsDAO.findAll();
        for (Mechanic mechanic : availableMechanics) {
            this.allMechanics.add(new SelectItem(mechanic, mechanic.getName()));
        }
    }
}
