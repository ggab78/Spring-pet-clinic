package com.gabriel.springpetclinic.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "vet_specialities", joinColumns = @JoinColumn(name = "vet_id"),
    inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

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

    public void addSpeciality(Speciality speciality){
        speciality.getVets().add(this);
        this.specialities.add(speciality);
    }

}
