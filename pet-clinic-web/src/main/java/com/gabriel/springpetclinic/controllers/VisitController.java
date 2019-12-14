package com.gabriel.springpetclinic.controllers;


import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;


    @InitBinder("pet")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("visit")
    public void initVisitBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new VisitValidator());
    }

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
    public String processNewVisitForm(@Valid Visit visit, Pet pet, BindingResult result, Model model){

        if (visit.isNew() && pet.findVisit(visit.getDescription(), visit.getDate()) != null){
            result.rejectValue("date", "duplicate", "already exists");
        }

        pet.addVisit(visit);
        if (result.hasErrors()) {
            model.addAttribute("visit", visit);
            return "pets/createOrUpdateVisitForm";
        } else {
            this.visitService.save(visit);
            return "redirect:/owners/"+pet.getOwner().getId();
        }
    }

}