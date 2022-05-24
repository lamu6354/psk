package com.laura.usecases;

import com.laura.interceptors.Logger;
import com.laura.service.LicenseNumberGenerator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.faces.context.FacesContext.getCurrentInstance;

@Named
@SessionScoped
    public class GenerateLicenseNumber implements Serializable {

    @Inject
    private LicenseNumberGenerator generator;

    private Future<Integer> generationTask = null;

    @Logger
    public String generateNewLicenseNumber() {
        Map<String, String> requestParams = getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        generationTask = generator.generateLicenseNumber();
        return "carDetails.xhtml?faces-redirect=true&licenseNumber=" + requestParams.get("licenseNumber");
    }

    public boolean isGenerationRunning() {
        return nonNull(generationTask) && !generationTask.isDone();
    }

    public String getGenerationStatus() throws ExecutionException, InterruptedException {
        if (isNull(generationTask)) {
            return null;
        } else if (isGenerationRunning()) {
            return "Generation is in progress";
        }
        return "Generated Car ID: " + generationTask.get();
    }
}
