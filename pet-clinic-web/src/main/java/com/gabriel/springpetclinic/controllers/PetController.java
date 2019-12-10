package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.Pet;
import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@AllArgsConstructor
@RequestMapping("/owners/{ownerId}")
public class PetController {

    PetTypeService petTypeService;
    PetService petService;
    OwnerService ownerService;

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.petTypeService.findAll();
    }

    /*
    *Instructs SpringMVC that instance owner of class Owner is find from ownerService by ownerId. This owner
    *can be use in methods inside PetController whenever we declare owner as method parameter.
    * Model attribute is also an attribute that can be used by thymeleaf
    */
    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return this.ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @RequestMapping("/pets/new")
    public String newPetForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.addPet(pet);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String processNewPetForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {

        if (pet.isNew() && owner.findPet(pet.getName(), pet.getBirthDate(),pet.getPetType()) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }

        owner.addPet(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            this.petService.save(pet);
            this.petTypeService.save(pet.getPetType());
            return "redirect:/owners/"+owner.getId();
        }
    }

    @RequestMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model){
        model.addAttribute("pet", petService.findById(petId));
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@PathVariable Long petId, Owner owner, @Valid Pet pet, BindingResult result, Model model) {

        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        } else {
            Pet actualPet = petService.findById(petId);
            actualPet.setName(pet.getName());
            actualPet.setBirthDate(pet.getBirthDate());
            actualPet.setPetType(pet.getPetType());
            this.petService.save(actualPet);
            this.petTypeService.save(actualPet.getPetType());
            return "redirect:/owners/"+owner.getId();
        }
    }

}
