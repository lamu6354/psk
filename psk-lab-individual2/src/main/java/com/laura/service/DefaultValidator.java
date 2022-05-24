package com.laura.service;

import com.laura.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import static java.lang.Character.isUpperCase;

@Alternative
@ApplicationScoped
public class DefaultValidator implements NameValidator {

    @Override
    @Logger
    public boolean isNameValid(String name) {
        return name.length() < 10 && isUpperCase(name.charAt(0));
    }
}
