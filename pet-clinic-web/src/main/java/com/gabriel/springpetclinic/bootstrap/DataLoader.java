package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetTypeService;
import com.gabriel.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
// first owner
        Owner owner = new Owner("mat","gam");
        owner.setAddress("traugutta3");
        owner.setCity("DG");
        owner.setTelephone("693802123");
        Pet pet = new Pet();
        pet.setPetType(savedDogPetType);
        pet.setBirthDate(LocalDate.now());
        pet.setName("Hugo");
        pet.setOwner(owner);
        owner.getPets().add(pet);
        ownerService.save(owner);
//seond owner
        owner = new Owner("gab","gam");
        owner.setAddress("traugutta3");
        owner.setCity("DG");
        owner.setTelephone("693802123");
        pet = new Pet();
        pet.setPetType(savedDogPetType);
        pet.setBirthDate(LocalDate.now());
        pet.setName("Hugo2");
        pet.setOwner(owner);
        owner.getPets().add(pet);
        ownerService.save(owner);
//third owner
        owner = new Owner("ewa","lewa");
        owner.setAddress("traugutta2");
        owner.setCity("WAWA");
        owner.setTelephone("6938027345");
        pet = new Pet();
        pet.setPetType(savedCatPetType);
        pet.setBirthDate(LocalDate.of(2018,5,7));
        pet.setName("Hugo1");
        pet.setOwner(owner);
        owner.getPets().add(pet);
        ownerService.save(owner);

        System.out.println("Owners loaded");

        Vet vet = new Vet("tom","kos");
        vetService.save(vet);
        vet = new Vet("tim","moine");
        vetService.save(vet);

        System.out.println("Vets loaded");

    }
}
