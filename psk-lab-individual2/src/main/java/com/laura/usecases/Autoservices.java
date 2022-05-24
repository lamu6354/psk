package com.laura.usecases;

import com.laura.entities.Autoservice;
import com.laura.persistence.AutoservicesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class Autoservices implements Serializable {

    @Inject
    private AutoservicesDAO autoservicesDAO;

    @Getter
    @Setter
    private Autoservice autoserviceToCreate = new Autoservice();

    @Getter
    private List<Autoservice> allAutoservices;

    @PostConstruct
    private void init() {
        loadAllAutoseervices();
    }

    @Transactional
    public String createAutoservice() {
        autoservicesDAO.persist(autoserviceToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllAutoseervices() {
        this.allAutoservices = autoservicesDAO.loadAll();
    }
}
