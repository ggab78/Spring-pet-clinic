package com.gabriel.springpetclinic.bootstrap;

import com.gabriel.springpetclinic.repositories.OwnerRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootstrapOwners implements ApplicationListener<ContextRefreshedEvent> {

private final OwnerRepository ownerRepository;
private final DataLoader dataLoader;



    public BootstrapOwners(OwnerRepository ownerRepository, DataLoader dataLoader) {
        this.ownerRepository = ownerRepository;
        this.dataLoader = dataLoader;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ownerRepository.saveAll(dataLoader.getOwnerService().findAll());

    }
}
