package com.gabriel.springpetclinic.repositories;

import com.gabriel.springpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner,Long>{

    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameContainingIgnoringCase(String lastName);

}
