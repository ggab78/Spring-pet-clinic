package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetTypeService;
import com.gabriel.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner = new Owner("mat","gam");
        ownerService.save(owner);
        owner = new Owner("gab","gam");
        ownerService.save(owner);
        owner = new Owner("ewa","lewa");
        ownerService.save(owner);

        System.out.println("Owners loaded");

        Vet vet = new Vet("tom","kos");
        vetService.save(vet);
        vet = new Vet("tim","moine");
        vetService.save(vet);

        System.out.println("Vets loaded");

    }
}
