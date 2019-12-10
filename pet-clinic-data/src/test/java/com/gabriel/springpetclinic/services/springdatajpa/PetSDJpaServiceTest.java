package com.gabriel.springpetclinic.services.springdatajpa;

import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PetSDJpaServiceTest {


    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSDJpaService petSDJpaService;

    public static final Long PET_ID = 2L;
    public static final String PET_NAME = "Hugo";
    Pet returnPet;

    @BeforeEach
    void setUp() {
        returnPet = Pet.builder().id(PET_ID).name(PET_NAME).build();
    }


    @Test
    void save() {
        when(petRepository.save(any())).thenReturn(returnPet);

        //when
        Pet savedPet = petSDJpaService.save(new Pet());

        //then
        assertNotNull(savedPet);
        assertEquals(PET_ID,savedPet.getId());
        assertEquals(PET_NAME,savedPet.getName());
        verify(petRepository, times(1)).save(any());

    }


    @Test
    void findById() {

        when(petRepository.findById(anyLong())).thenReturn(Optional.of(returnPet));
        Pet foundPet = petSDJpaService.findById(112L);

        assertNotNull(foundPet);
        assertEquals(PET_ID,foundPet.getId());
        verify(petRepository, times(1)).findById(anyLong());

    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}