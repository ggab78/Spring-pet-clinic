package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Visit;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VisitValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public boolean supports(Class<?> clazz) {
        return Visit.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Visit visit = (Visit) obj;

        // description validation
        if (!StringUtils.hasLength(visit.getDescription())) {
            errors.rejectValue("description", REQUIRED, REQUIRED);
        }

        // birth date validation
        if (visit.getDate() == null) {
            errors.rejectValue("date", REQUIRED, REQUIRED);
        }

        // birth date not later then
//        LocalDate now = LocalDate.now();
//        if(visit.getDate().compareTo(now)<0){
//            errors.rejectValue("date",REQUIRED, REQUIRED);
//        }
    }
}