package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    public void addPet(Pet pet){
        pet.setOwner(this);
        System.out.println("Owner name = " + pet.getOwner().getLastName());
        this.pets.add(pet);
    }

    public Pet findPet(String name, LocalDate birthDate, PetType petType){

        for(Pet pet : pets){
            if(pet.getPetType().equals(petType) && pet.getName().equals(name) && pet.getBirthDate().equals(birthDate)){
                return pet;
            }

        }
        return null;
    }
}
