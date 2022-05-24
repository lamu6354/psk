package com.laura.decorator;

import com.laura.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;

import static java.lang.Integer.parseInt;

@ApplicationScoped
public class CarLicenseNumberFixerImpl implements CarLicenseNumberFixer {
    @Override
    @Logger
    public Integer fixStudentId(int id) {
        return parseInt(id + "222");
    }
}
