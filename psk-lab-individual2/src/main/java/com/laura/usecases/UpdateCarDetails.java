package com.laura.usecases;

import com.laura.entities.Car;
import com.laura.persistence.CarsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static javax.faces.context.FacesContext.getCurrentInstance;

@Named
@Getter
@Setter
@ViewScoped
public class UpdateCarDetails implements Serializable {

    private Car car;

    @Inject
    private CarsDAO carsDAO;

    @PostConstruct
    private void init() {
        System.out.println("UpdateCarDetails INIT CALLED");
        Map<String, String> requestParams = getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        int licenseNumber = parseInt(requestParams.get("licenseNumber"));
        this.car = carsDAO.findOne(licenseNumber);
    }

    @Transactional
    public String updateLicenseNumber() {
        try {
            carsDAO.update(this.car);
        } catch (OptimisticLockException e) {
            return "/carDetails.xhtml?faces-redirect=true&licenseNumber=" + this.car.getId() + "&error=optimistic-lock-exception";
        }
        return "cars.xhtml?autoserviceId=" + this.car.getAutoservice().getId() + "&faces-redirect=true";
    }

}
