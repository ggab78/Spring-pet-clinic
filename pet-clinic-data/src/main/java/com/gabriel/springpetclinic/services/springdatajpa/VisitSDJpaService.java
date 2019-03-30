package com.gabriel.springpetclinic.services.springdatajpa;


import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.repositories.VisitRepository;
import com.gabriel.springpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSDJpaService implements VisitService {

    private final VisitRepository visitRepository;

    public VisitSDJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit save(Visit object) {
        return visitRepository.save(object);

    }

    @Override
    public Visit findById(Long aLong) {
        Optional<Visit> visitOptional = visitRepository.findById(aLong);
        if(visitOptional.isPresent()){
            return visitOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visit -> visits.add(visit));
        return visits;
    }

    @Override
    public void delete(Visit object) {
        visitRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        visitRepository.deleteById(aLong);
    }
}
