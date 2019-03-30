package com.gabriel.springpetclinic.services.springdatajpa;

import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.repositories.PetRepository;
import com.gabriel.springpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {

    private final PetRepository petRepository;

    public PetSDJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet save(Pet object) {
        return petRepository.save(object);
    }

    @Override
    public Pet findById(Long aLong) {

        Optional<Pet> petOptional = petRepository.findById(aLong);
        if(petOptional.isPresent()){
            return petOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public Set<Pet> findAll() {

        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pet -> pets.add(pet));
        return pets;
    }

    @Override
    public void delete(Pet object) {
        petRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        petRepository.deleteById(aLong);
    }
}
