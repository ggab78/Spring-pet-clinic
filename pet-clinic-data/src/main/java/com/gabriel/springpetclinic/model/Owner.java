package com.gabriel.springpetclinic.model;

import java.util.Set;

public class Owner extends Person {



    private Set<Pet> pets;



    public Owner() {
    }

    public Owner(String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
    }



    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

}
