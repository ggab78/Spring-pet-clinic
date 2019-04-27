package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {


    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @Mock
    ModelAndView modelAndView;

    @InjectMocks
    OwnerController ownerController;

    Set<Owner> ownerSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void showOwnerDetails() throws Exception {

        Long ownerId = 3L;
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(ownerId).build());

        ownerController.showOwnerDetails(ownerId);

        verify(ownerService, times(1)).findById(anyLong());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerdetails"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("id", is(ownerId))));
    }

    @Test
    void findOwner() throws Exception{

        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyZeroInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception{
        when(ownerService.findAllByLastNameContainingIgnoringCase(anyString())).thenReturn(ownerSet);

        mockMvc.perform(get("/owners/getFindOwner"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        when(ownerService.findAllByLastNameContainingIgnoringCase(anyString())).thenReturn(Set.of(Owner.builder().id(4L).build()));

        mockMvc.perform(get("/owners/getFindOwner"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/4"));
    }
}