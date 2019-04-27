package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;
    private final PetTypeService petTypeService;

    public OwnerServiceMap(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        for (Owner o : map.values()) {
            if(o.getLastName().equals(lastName)){
                return o;
            }
        }
        return null;
    }

    @Override
    public Set<Owner> findAllByLastNameContainingIgnoringCase(String lastName) {
        Set<Owner> owners = new HashSet<>();
        for (Owner o : map.values()) {
            if(o.getLastName().equals(lastName)){
                owners.add(o);
            }
        }
        return owners;
        //todo ContainingIgnoringCase functionality needs to be introduced
    }

    @Override
    public Owner save(Owner object) {
        if(object!=null){
            if (object.getPets()!=null){
                object.getPets().forEach(pet -> {
                    if(pet.getPetType()!=null){
                        if(pet.getPetType().getId()==null){
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    }else{
                        throw new RuntimeException("Pet must have PetType");
                    }
                    if(pet.getId()==null){
                        // object.getPets().remove(pet);//some difference introduced when compared with tutorial
                        // object.getPets().add(petService.save(pet));//some difference introduced when compared with tutorial
                    petService.save(pet);
                    }
                });
            }
        }
        return super.save(object);
    }

}
