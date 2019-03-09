package com.gabriel.springpetclinic.model;

public class Owner extends Person {

    public Owner() {
    }

    public Owner(Long id, String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setId(id);
    }

}
