package com.gabriel.springpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

@RequestMapping({"/vets/index","/vets/index.html", "/vets/", "/vets"})
    public String listVets(){

        return "vets/index";
    }

}
