package com.gabriel.springpetclinic.services;

import com.gabriel.springpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner,Long> {

    Owner findByLastName(String lastName);
}
