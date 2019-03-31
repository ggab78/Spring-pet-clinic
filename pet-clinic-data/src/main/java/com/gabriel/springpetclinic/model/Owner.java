package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owners")
public class Owner extends Person {

    private String address;
    private String city;
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public Owner(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void addPet(Pet pet){
        pet.setOwner(this);
        System.out.println("Owner name = " + pet.getOwner().getLastName());
        this.pets.add(pet);
    }
}
