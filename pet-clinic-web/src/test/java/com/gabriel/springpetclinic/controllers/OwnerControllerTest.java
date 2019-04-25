package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
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
    public void testMockMVC() throws Exception {

        when(ownerService.findAll()).thenReturn(ownerSet);
        mockMvc.perform(get("/owners"))//url as provided in ownerController request mapping
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners",hasSize(2)));
    }

    @Test
    void listOwners() {


        //when
        when(ownerService.findAll()).thenReturn(ownerSet);
        ArgumentCaptor<Set<Owner>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //then
        String str = ownerController.listOwners(model);
        assertEquals("owners/index",str);
        verify(model,times(1)).addAttribute(eq("owners"), argumentCaptor.capture());
        assertEquals(2, argumentCaptor.getValue().size());
        verify(ownerService,times(1)).findAll();
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
}