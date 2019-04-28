package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PetType extends BaseEntity{

    private String name;

    @Builder
    public PetType(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}