package com.gabriel.springpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity{

    @Builder
    public Pet(Long id, String name, Owner owner, PetType petType,LocalDate birthDate, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.owner=owner;
        this.petType=petType;
        this.birthDate=birthDate;
        if(visits!=null || !visits.isEmpty()) {
            this.visits = visits;
        }
    }

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits= new HashSet<>();



    public void addVisit(Visit visit){
        visit.setPet(this);
        this.visits.add(visit);
    }

    public Visit findVisit(String description, LocalDate date){
        for(Visit visit : visits){
            if(visit.getDescription().equals(description) && visit.getDate().equals(date)){
                return visit;
            }
        }
        return null;
    }
}
