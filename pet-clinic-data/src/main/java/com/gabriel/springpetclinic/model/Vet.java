package com.gabriel.springpetclinic.model;

import java.util.Set;

public class Vet extends Person {

    private Set<Speciality> specialities;

    public Vet() {
    }

    public Vet(String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
    }


    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }
}
