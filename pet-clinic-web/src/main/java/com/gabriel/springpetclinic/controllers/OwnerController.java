package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.model.OwnerCommand;
import com.gabriel.springpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
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
        model.addAttribute("owner", new OwnerCommand().OwnerToOwnerCommand(ownerService.findById(ownerId)));
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String editOwnerProcess(@Valid @ModelAttribute("owner") OwnerCommand source, BindingResult result, @PathVariable Long ownerId) {
    //BindingResult must be directly after ModelAttribute

        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
            Owner existingOwner = ownerService.findById(ownerId);
            existingOwner = source.OwnerCommandToOwner(existingOwner);
            Owner savedOwner = ownerService.save(existingOwner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @RequestMapping("/new")
    public String newOwner(Model model) {
        model.addAttribute("owner", new OwnerCommand());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String newOwnerProcess(@Valid @ModelAttribute("owner") OwnerCommand source, BindingResult result) {

        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
        Owner savedOwner = ownerService.save(source.OwnerCommandToOwner(new Owner()));
        return "redirect:/owners/"+savedOwner.getId();
        }
    }

    @GetMapping("/api/owners")
    public @ResponseBody Set<Owner> getJsonOwners(){
        return ownerService.findAll();
    }
}
