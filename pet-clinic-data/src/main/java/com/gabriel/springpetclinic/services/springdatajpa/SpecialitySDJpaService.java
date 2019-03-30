package com.gabriel.springpetclinic.services.springdatajpa;

import com.gabriel.springpetclinic.model.Speciality;
import com.gabriel.springpetclinic.repositories.SpecialityRepository;
import com.gabriel.springpetclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialitySDJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Speciality save(Speciality object) {
        return specialityRepository.save(object);
    }

    @Override
    public Speciality findById(Long aLong) {
        Optional<Speciality>specialityOptional = specialityRepository.findById(aLong);
        if(specialityOptional.isPresent()){
            return specialityOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(speciality -> specialities.add(speciality));
        return specialities;
    }

    @Override
    public void delete(Speciality object) {
        specialityRepository.delete(object);

    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }
}
