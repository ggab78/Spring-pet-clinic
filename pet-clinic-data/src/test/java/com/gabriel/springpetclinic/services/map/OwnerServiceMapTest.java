package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerServiceMapTest {



    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    OwnerServiceMap ownerServiceMap;

    final Long ownerId = 1L;
    final String lastName = "smith";

    @BeforeEach
    void setUp() {
        ownerServiceMap.save(Owner.builder().id(ownerId).lastName(this.lastName).build());
    }

    @Test
    void findById() {
        Owner o = ownerServiceMap.findById(ownerId);
        assertEquals(ownerId, o.getId());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();
        assertEquals(1,ownerSet.size());
    }

    @Test
    void delete() {
        Owner o = ownerServiceMap.findById(ownerId);
        ownerServiceMap.delete(o);
        assertTrue(ownerServiceMap.map.isEmpty());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);
        assertTrue(ownerServiceMap.map.isEmpty());
    }

    @Test
    void findByLastName() {
        assertEquals(lastName, ownerServiceMap.findByLastName(lastName).getLastName());
        assertEquals(ownerId, ownerServiceMap.findByLastName(lastName).getId());
    }

    @Test
    void save() {

        //create PetType with null id
        PetType returnedPetType = PetType.builder().name("dog").build();

        //create Pet with null id
        Pet returnedPet = Pet.builder().name("Hugo").petType(returnedPetType).build();

        Set<Pet> pets = new HashSet<>();
        pets.add(returnedPet);

        Owner owner = Owner.builder().lastName("XXX").pets(pets).build();

        //when(petService.save(any())).thenReturn(returnedPet);
        //when(petTypeService.save(any())).thenReturn(returnedPetType);

        Owner savedOwner = ownerServiceMap.save(owner);

        assertEquals(2,ownerServiceMap.findAll().size());//in setUp method first Owner is saved
        assertEquals("XXX", savedOwner.getLastName());
        assertNotNull(savedOwner.getId());
        assertEquals(1, savedOwner.getPets().size());

        verify(petService,times(1)).save(any());
        verify(petTypeService,times(1)).save(any());

    }


}