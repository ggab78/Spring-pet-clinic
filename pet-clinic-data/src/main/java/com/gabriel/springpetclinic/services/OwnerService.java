package com.gabriel.springpetclinic.services;

import com.gabriel.springpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner,Long> {

    Owner findByLastName(String lastName);
    Set<Owner> findAllByLastNameContainingIgnoringCase(String lastName);

}

