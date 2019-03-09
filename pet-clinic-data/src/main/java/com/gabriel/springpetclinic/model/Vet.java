package com.gabriel.springpetclinic.model;

public class Vet extends Person {

    public Vet() {
    }

    public Vet(Long id, String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setId(id);
    }


}
