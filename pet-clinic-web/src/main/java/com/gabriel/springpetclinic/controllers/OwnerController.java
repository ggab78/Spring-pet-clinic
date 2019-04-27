package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping({"/owners"})
@Controller
public class OwnerController {

private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }


    @RequestMapping("/{ownerId}")
    public ModelAndView showOwnerDetails(@PathVariable Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerdetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @RequestMapping("/find")
    public String findOwnerInit(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @RequestMapping("/getFindOwner")
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        // allow parameter-less GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }
        // find owners by last name
        Collection<Owner> results = this.ownerService.findAllByLastNameContainingIgnoringCase(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }
        else {
            // multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @RequestMapping("/{ownerId}/edit")
    public String editOwner(@PathVariable Long ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(ownerId));
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String editOwnerProcess(@Valid Owner source, @PathVariable Long ownerId, BindingResult result) {
        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
            Owner existingOwner = ownerService.findById(ownerId);
            existingOwner.setFirstName(source.getFirstName());
            existingOwner.setLastName(source.getLastName());
            existingOwner.setCity(source.getCity());
            existingOwner.setTelephone(source.getTelephone());
            existingOwner.setAddress(source.getAddress());
            Owner savedOwner = ownerService.save(existingOwner);
            return "redirect:/owners/"+savedOwner.getId();
        }
    }

    @RequestMapping("/new")
    public String newOwner(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String newOwnerProcess(@Valid Owner source, BindingResult result) {
        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
        Owner savedOwner = ownerService.save(source);
        return "redirect:/owners/"+savedOwner.getId();
        }
    }
}
