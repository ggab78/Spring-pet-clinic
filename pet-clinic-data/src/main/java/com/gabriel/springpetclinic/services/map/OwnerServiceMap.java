package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import com.gabriel.springpetclinic.services.PetService;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;


@Service
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
//
//    @Override
//    public Owner findById(Long id) {
//        return super.findById(id);
//    }
//
//    @Override
//    public Set findAll() {
//        return super.findAll();
//    }
//
//    @Override
//    public void delete(Owner object) {
//        super.delete(object);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        super.deleteById(id);
//    }
}
