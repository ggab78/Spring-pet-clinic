package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping({"","/","/index","/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/{ownerId}")
    public ModelAndView showOwnerDetails(@PathVariable Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerdetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

}
