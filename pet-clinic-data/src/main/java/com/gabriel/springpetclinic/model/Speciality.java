package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Speciality extends BaseEntity {

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "specialities")
    private Set<Vet> vets = new HashSet<>();
    private String description;

}
