package com.gabriel.springpetclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Speciality extends BaseEntity {

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "specialities")
    private Set<Vet> vets = new HashSet<>();
    private String description;

    public Set<Vet> getVets() {
        return vets;
    }
    public void setVets(Set<Vet> vets) {
        this.vets = vets;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
