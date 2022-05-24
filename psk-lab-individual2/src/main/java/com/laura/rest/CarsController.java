package com.laura.rest;

import com.laura.decorator.CarLicenseNumberFixer;
import com.laura.entities.Autoservice;
import com.laura.entities.Car;
import com.laura.persistence.AutoservicesDAO;
import com.laura.persistence.CarsDAO;
import com.laura.rest.resource.CarDto;
import com.laura.service.DefaultValidator;
import com.laura.service.NameValidator;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static java.net.URI.create;
import static java.util.Objects.isNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Path("/cars")
public class CarsController {

    @Inject
    @Getter
    @Setter
    private CarsDAO carsDAO;

    @Inject
    private NameValidator validator;

    @Inject
    private DefaultValidator defaultValidator;

    @Inject
    private CarLicenseNumberFixer idFixer;

    @Inject
    private AutoservicesDAO autoservicesDAO;

    @Context
    private UriInfo servletRequest;

    @POST
    @Transactional
    @Produces(APPLICATION_JSON)
    public Response createCar(CarDto carDto) {
        Autoservice autoservice = autoservicesDAO.findByName(carDto.getAutoserviceName());
        Car carToCreate = mapCarToEntity(carDto, autoservice);
        carsDAO.persist(carToCreate);
        final String createdUri = servletRequest.getAbsolutePath() + "/" + carToCreate.getId();
        return created(create(createdUri)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        Car car = carsDAO.findOne(id);
        if (isNull(car)) {
            return status(NOT_FOUND).build();
        }
        CarDto carDto = new CarDto(car.getCarModel(), car.getLicenseNumber(), car.getAutoservice().getTitle());
        return ok(carDto).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Integer id, CarDto carDto) {
        try {
            Car existingCar = carsDAO.findOne(id);
            if (isNull(existingCar)) {
                return status(NOT_FOUND).build();
            }
            boolean nameValid = defaultValidator.isNameValid(carDto.getCarModel());
            boolean nameValid2 = validator.isNameValid(carDto.getCarModel());

            existingCar.setCarModel(carDto.getCarModel());
            existingCar.setLicenseNumber(carDto.getLicenseNumber());
            carsDAO.update(existingCar);
            return ok().build();
        } catch (OptimisticLockException e) {
            return status(CONFLICT).build();
        }
    }

    private Car mapCarToEntity(CarDto carDto, Autoservice autoservice) {
        Car carToCreate = new Car();
        carToCreate.setLicenseNumber(carDto.getLicenseNumber());
        carToCreate.setCarModel(carDto.getCarModel());
        carToCreate.setAutoservice(autoservice);
        return carToCreate;
    }
}
