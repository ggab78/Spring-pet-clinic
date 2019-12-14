package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @Mock
    Model model;

    @InjectMocks
    VisitController visitController;

    Pet pet;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
        Owner owner = Owner.builder().id(1L).firstName("gab").build();
        Visit visit = Visit.builder().description("existing").date(LocalDate.of(2020, 01, 01)).build();
        pet = Pet.builder().id(2L).name("hugo").build();
        pet.addVisit(visit);
        owner.addPet(pet);
    }

    @Test
    void initNewVisitForm()throws Exception {

        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(model().attribute("pet", hasProperty("id", is(2L))));
    }

    @Test
    void processNewVisitFormHasErrors() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "")
                .param("date", "2019-01-01")
        )
                .andExpect(model().attributeHasErrors("visit"))
                .andExpect(model().attributeHasFieldErrorCode("visit", "description", "required"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void processNewVisitFormHasErrorsExistingVisit() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "existing")
                .param("date", "2020-01-01")
        )
                .andExpect(model().attributeHasErrors("visit"))
                .andExpect(model().attributeHasFieldErrorCode("visit", "date", "duplicate"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }




    @Test
    void processNewVisitFormSuccess() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "bvbv")
                .param("date", "2022-01-01")
        )
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/"+pet.getOwner().getId()));
    }

}