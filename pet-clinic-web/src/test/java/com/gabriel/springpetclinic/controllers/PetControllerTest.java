package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Set<PetType> petTypes;
    Owner owner;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("dog").build());
        petTypes.add(PetType.builder().id(2L).name("cat").build());
        owner = new Owner();
        Long ownerId = 23L;
        owner.setId(ownerId);
        Pet pet = Pet
                .builder()
                .petType(PetType.builder().id(1L).name("dog").build())
                .birthDate(LocalDate.of(2019, 01, 01))
                .name("hugo").build();
        owner.addPet(pet);

        when(ownerService.findById(anyLong())).thenReturn(owner);

    }

    @Test
    void newPetForm() throws Exception {

        when(petTypeService.findAll()).thenReturn(petTypes);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("owner", hasProperty("id", is(23L))));
    }

    @Test
    void processNewPetFormSuccess() throws Exception {

        mockMvc.perform(post("/owners/{ownerId}/pets/new", 23L)
                .param("name", "hugo_2")
                .param("petType", "dog")
                .param("birthDate", "2019-01-01"))
                .andExpect(model().attributeHasNoErrors("owner"))
                .andExpect(model().attributeHasNoErrors("pet"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/23"));
    }

    @Test
    public void processNewPetFormHasErrors() throws Exception {

        mockMvc.perform(post("/owners/1/pets/new")
                .param("name", "Betty")
                .param("birthDate", "2015-02-12")
        )
                .andExpect(model().attributeHasNoErrors("owner"))
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("pet", "petType"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "petType", "required"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }
}