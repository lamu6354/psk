package com.laura.service;

import com.laura.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
public class DumbValidator extends DefaultValidator {
    @Override
    @Logger
    public boolean isNameValid(String name) {
        return name.length() == 5;
    }
}
