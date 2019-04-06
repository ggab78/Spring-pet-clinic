package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    final Long ownerId = 1L;
    final String lastName = "smith";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerServiceMap = new OwnerServiceMap(petService, petTypeService);
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
        Owner owner = Owner.builder().lastName("XXX").build();
        assertEquals("XXX", ownerServiceMap.save(owner).getLastName());
        assertNotNull(ownerServiceMap.save(owner).getId());
    }

}