package com.gabriel.springpetclinic.services.springdatajpa;

import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.repositories.VetRepository;
import com.gabriel.springpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJpaService implements VetService {

    private final VetRepository vetRepository;

    public VetSDJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }

    @Override
    public Vet findById(Long aLong) {
        Optional<Vet> vetOptional = vetRepository.findById(aLong);
        if(vetOptional.isPresent()){
            return vetOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vet -> vets.add(vet));
        return vets;
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);

    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById(aLong);
    }
}
