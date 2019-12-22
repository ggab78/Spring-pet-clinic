package com.gabriel.springpetclinic.controllers;

import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Slf4j
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets","/vets.html","/vets/index","/vets/index.html"})
    public String listVets(Model model){

        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

    @GetMapping("/api/vet/{id}")
    public @ResponseBody Vet getJsonVetById(@PathVariable Long id){
        return vetService.findById(id);
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getJsonVets(){
        return vetService.findAll();
    }
}
