package com.gabriel.springpetclinic.services.springdatajpa;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    public static final Long OWNER_ID = 1L;
    public static final String LAST_NAME = "Tom";

    Owner returnOwner;

    @BeforeEach
    void setUp() {

        returnOwner = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {


        //when
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        Owner owner = ownerSDJpaService.findByLastName(LAST_NAME);

        //then
        assertEquals(LAST_NAME,owner.getLastName());
        verify(ownerRepository, times(1)).findByLastName(anyString());

    }

    @Test
    void save() {

        //when
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner owner = ownerSDJpaService.save(returnOwner);

        //then
        assertNotNull(owner);
        assertEquals(OWNER_ID,owner.getId());
        verify(ownerRepository, times(1)).save(any());

    }

    @Test
    void findById() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner = ownerSDJpaService.findById(OWNER_ID);

        //then
        assertEquals(OWNER_ID,owner.getId());
        verify(ownerRepository, times(1)).findById(anyLong());

    }
    @Test
    void findByIdNotFound() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerSDJpaService.findById(OWNER_ID);

        //then
        assertNull(owner);
        verify(ownerRepository, times(1)).findById(anyLong());

    }

    @Test
    void findAll() {

        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        //when
        when(ownerRepository.findAll()).thenReturn(ownerSet);

        //then
        assertEquals(2,ownerSDJpaService.findAll().size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void delete() {

        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void findAllByLastNameContainingIgnoringCaseNoOwners() {

        Set<Owner> returnedOwners = new HashSet<>();
        returnedOwners.add(returnOwner);
        when(ownerRepository.findAllByLastNameContainingIgnoringCase(anyString())).thenReturn(returnedOwners);

        assertEquals(1, ownerSDJpaService.findAllByLastNameContainingIgnoringCase("").size());
        verify(ownerRepository, times(1)).findAllByLastNameContainingIgnoringCase(anyString());

    }
}