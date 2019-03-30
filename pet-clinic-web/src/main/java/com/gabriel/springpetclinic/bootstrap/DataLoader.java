package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.model.*;
import com.gabriel.springpetclinic.repositories.OwnerRepository;
import com.gabriel.springpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final PetService petService;
    private final SpecialityService specialityService;
    private final VisitService visitService;


    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, PetService petService, SpecialityService specialityService, VisitService visitService, OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if(count==0) {
            loadData();
        }
    }



    public void loadData() {
        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
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
        //pet.setOwner(owner);
        owner.addPet(pet);
        petService.save(pet);
        ownerService.save(owner);


//second owner
        owner = new Owner("gab","gam");
        owner.setAddress("traugutta3");
        owner.setCity("DG");
        owner.setTelephone("693802123");
        pet = new Pet();
        pet.setPetType(savedDogPetType);
        pet.setBirthDate(LocalDate.now());
        pet.setName("Hugo2");
        //pet.setOwner(owner);
        owner.addPet(pet);
        petService.save(pet);
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
        //pet.setOwner(owner);
        owner.getPets().add(pet);
        petService.save(pet);
        ownerService.save(owner);

        System.out.println("Owners loaded");


//first vet
        Vet vet = new Vet("tom","kos");
        Speciality speciality = new Speciality();
        speciality.setDescription("dentist");
        vet.getSpecialities().add(speciality);
        vetService.save(vet);


//second vet
        vet = new Vet("tim","moine");
        speciality = new Speciality();
        speciality.setDescription("surgeon");
        vet.getSpecialities().add(speciality);
        vetService.save(vet);
        System.out.println("Vets loaded");

//first visit
        Visit visit = new Visit();
        visit.setDate(LocalDate.of(2019, 4, 7));
        visit.setDescription("first visit");
        visit.setPet(pet);
        visitService.save(visit);

    }

    public OwnerService getOwnerService() {
        return ownerService;
    }
    public PetService getPetService() {
        return petService;
    }
    public PetTypeService getPetTypeService() {
        return petTypeService;
    }
    public SpecialityService getSpecialityService() {
        return specialityService;
    }
    public VetService getVetService() {
        return vetService;
    }
    public VisitService getVisitService() {
        return visitService;
    }

}
