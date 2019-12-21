package com.gabriel.springpetclinic.controllers;


import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.VisitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/owners/*/pets/{petId}")
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

//    @InitBinder("visit")
//    public void initVisitBinder(WebDataBinder dataBinder) {
//        dataBinder.setValidator(new VisitValidator());
//    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable Long petId) {
        return petService.findById(petId);
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(Pet pet, Model model){
        Visit visit = new Visit();
        pet.addVisit(visit);
        model.addAttribute("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(Pet pet, @Valid @ModelAttribute("visit") Visit visit, BindingResult result, Model model){
//    log.debug(visit.getDescription());
//    log.debug(visit.getDate().toString());

        if (result.hasErrors()) {
            model.addAttribute("pet",pet);
            return "pets/createOrUpdateVisitForm";
        }

        if (visit.isNew() && pet.findVisit(visit.getDescription(), visit.getDate()) != null){
            result.rejectValue("date", "duplicate", "already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("pet",pet);
            return "pets/createOrUpdateVisitForm";
        }
            pet.addVisit(visit);
            visitService.save(visit);
            return "redirect:/owners/"+pet.getOwner().getId();

    }

}