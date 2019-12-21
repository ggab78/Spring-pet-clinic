package com.gabriel.springpetclinic.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerCommand {

    private String address;
    private String city;
    private String telephone;

    @NotBlank(message = "Please provide first name")
    private String firstName;

    @NotBlank(message = "Please provide last name")
    private String lastName;

    public Owner OwnerCommandToOwner(Owner owner) {
        owner.setFirstName(this.firstName);
        owner.setLastName(this.lastName);
        owner.setAddress(this.address);
        owner.setCity(this.city);
        owner.setTelephone(this.telephone);
        return owner;
    }

    public OwnerCommand OwnerToOwnerCommand(Owner owner) {
        this.setFirstName(owner.getFirstName());
        this.setLastName(owner.getLastName());
        this.setAddress(owner.getAddress());
        this.setCity(owner.getCity());
        this.setTelephone(owner.getTelephone());

        return this;
    }

}
