package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

private final OwnerRepository ownerRepository;
private final PetRepository petRepository;
private final PetTypeRepository petTypeRepository;
private final SpecialityRepository specialityRepository;
private final VetRepository vetRepository;
private final VisitRepository visitRepository;
private final DataLoader dataLoader;


    public Bootstrap(OwnerRepository ownerRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialityRepository specialityRepository, VetRepository vetRepository, VisitRepository visitRepository, DataLoader dataLoader) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialityRepository = specialityRepository;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
        this.dataLoader = dataLoader;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        dataLoader.loadData();

        System.out.println("______________________________________________________________________");
        for(Visit visit : dataLoader.getVisitService().findAll() ){
            System.out.println(visit.getDescription());
        }
        //petTypeRepository.saveAll(dataLoader.getPetTypeService().findAll());
        ownerRepository.saveAll(dataLoader.getOwnerService().findAll());
        //petRepository.saveAll(dataLoader.getPetService().findAll());
        //visitRepository.saveAll(dataLoader.getVisitService().findAll());

    }
}
