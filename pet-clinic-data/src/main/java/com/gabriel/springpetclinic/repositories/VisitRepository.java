package com.gabriel.springpetclinic.repositories;

import com.gabriel.springpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit,Long> {
}
