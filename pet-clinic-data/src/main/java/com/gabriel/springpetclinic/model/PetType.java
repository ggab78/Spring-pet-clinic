package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PetType extends BaseEntity{

    private String name;

    @Override
    public String toString() {
        return this.getName();
    }
}