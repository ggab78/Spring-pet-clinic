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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
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

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Set<PetType> petTypes;
    Owner owner;
    Pet pet;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("dog").build());
        petTypes.add(PetType.builder().id(2L).name("cat").build());
        owner = new Owner();
        Long ownerId = 1L;
        owner.setId(ownerId);
        owner.setFirstName("gab");
        pet = Pet
                .builder()
                .petType(PetType.builder().id(1L).name("dog").build())
                .birthDate(LocalDate.of(2019, 01, 01))
                .id(1L)
                .name("hugo").build();
        owner.addPet(pet);

    }

    @Test
    void newPetForm() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }


    @Test
    void processNewPetFormSuccess() throws Exception {
        when(petTypeService.save(ArgumentMatchers.any())).thenReturn(pet.getPetType());
        when(petService.save(ArgumentMatchers.any())).thenReturn(pet);
        when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        petController.processNewPetForm(owner, pet, bindingResult, model);

        verify(petService).save(ArgumentMatchers.any());

        mockMvc.perform(post("/owners/1/pets/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "hugo_2")
                .param("petType", "dog")
                .param("birthDate", "2019-01-01")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeHasNoErrors("pet"))
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    public void processNewPetFormHasErrors() throws Exception {

        when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/pets/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
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



    @Test
    void initUpdateForm() throws Exception {

        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attribute("pet", hasProperty("id", is(1L))));
    }

    @Test
    void processUpdateFormSuccess() throws Exception {

        when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/1/edit")
                .param("name", "hugo_2")
                .param("petType", "dog")
                .param("birthDate", "2019-01-01"))
                .andExpect(model().attributeHasNoErrors("owner"))
                .andExpect(model().attributeHasNoErrors("pet"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService,times(1)).findById(anyLong());

    }

    @Test
    public void processUpdateFormHasErrors() throws Exception {

        when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

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