package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.OwnerCommand;
import com.gabriel.springpetclinic.services.OwnerService;
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
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {


    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

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
        when(ownerService.findAllByLastNameContainingIgnoringCase(anyString()))
                .thenReturn(Set.of(Owner.builder().id(4L).build()));

        mockMvc.perform(get("/owners/getFindOwner"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/4"));
    }

    @Test
    void editOwner() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().firstName("stf").build());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("stf"))));
    }

    @Test
    void editOwnerProcess() throws Exception {

        OwnerCommand source = OwnerCommand.builder().firstName("steve").lastName("jobs").build();
        Owner existing = Owner.builder().firstName("???").lastName("???").id(5L).build();
        Owner savedOwner = Owner.builder().firstName("steve").lastName("jobs").id(5L).build();

        when(ownerService.findById(anyLong())).thenReturn(existing);
        when(ownerService.save(any())).thenReturn(savedOwner);

        String str = ownerController.editOwnerProcess(source, bindingResult, 1L);

        assertEquals(source.getLastName(), savedOwner.getLastName());

        assertEquals("redirect:/owners/5", str);

        mockMvc.perform(post("/owners/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("lastName", "some")
                .param("firstName", "some")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/5"));
    }



    @Test
    void editOwnerProcessError() throws Exception {

        mockMvc.perform(post("/owners/1/edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lastName", "some")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }



    @Test
    void newOwner() throws Exception{

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("firstName", isEmptyOrNullString()));
    }

    @Test
    void newOwnerProcessSuccess() throws Exception {

        Owner source = Owner.builder().id(9L).build();
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(source);

        mockMvc.perform(post("/owners/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("lastName", "some")
                .param("firstName", "some"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/9"));

        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void newOwnerProcessError() throws Exception {

        mockMvc.perform(post("/owners/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("lastName", "some")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));

        verifyZeroInteractions(ownerService);
    }

}