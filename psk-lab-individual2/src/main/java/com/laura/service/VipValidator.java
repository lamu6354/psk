package com.laura.service;

import com.laura.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class VipValidator implements NameValidator {

    @Override
    @Logger
    public boolean isNameValid(String name) {
        return name.length() > 15;
    }
}
