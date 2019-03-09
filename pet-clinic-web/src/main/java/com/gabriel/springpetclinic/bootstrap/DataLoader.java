package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner = new Owner(1L,"mat","gam");
        ownerService.save(owner);
        owner = new Owner(2L,"gab","gam");
        ownerService.save(owner);

        System.out.println("Owners loaded");

        Vet vet = new Vet(1L,"tom","kos");
        vetService.save(vet);
        vet = new Vet(2L,"tim","moine");
        vetService.save(vet);

        System.out.println("Vets loaded");

    }
}
