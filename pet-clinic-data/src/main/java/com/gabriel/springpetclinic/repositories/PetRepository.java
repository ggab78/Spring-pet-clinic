package com.gabriel.springpetclinic.repositories;

import com.gabriel.springpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet,Long> {
}
