package com.gabriel.springpetclinic.repositories;

import com.gabriel.springpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet,Long> {
}
