package com.laura.decorator;

import com.laura.interceptors.Logger;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import static java.lang.Integer.parseInt;

@Decorator
public abstract class CarLicenseNumberFixerDecorator implements CarLicenseNumberFixer {

    @Inject
    @Delegate
    @Any
    private CarLicenseNumberFixer carLicenseNumberFixer;

    @Override
    @Logger
    public Integer fixStudentId(int id) {
        return parseInt(carLicenseNumberFixer.fixStudentId(id) + "999");
    }
}
