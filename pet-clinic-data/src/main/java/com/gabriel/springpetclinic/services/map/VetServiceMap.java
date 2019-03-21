package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Vet;
import com.gabriel.springpetclinic.services.SpecialityService;
import com.gabriel.springpetclinic.services.VetService;
import org.springframework.stereotype.Service;


@Service
public class VetServiceMap extends AbstractMapService<Vet,Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet object) {
        if(object!=null){
            if(object.getSpecialities()!=null){
                object.getSpecialities().forEach(speciality -> {
                    if(speciality.getId()==null){
                        specialityService.save(speciality);
                    }
                });
            }else{
                throw new RuntimeException("Vet must have Speciality");
            }
        }
        return super.save(object);
    }
//
//    @Override
//    public Vet findById(Long id) {
//        return super.findById(id);
//    }
//
//    @Override
//    public Set findAll() {
//        return super.findAll();
//    }
//
//    @Override
//    public void delete(Vet object) {
//        super.delete(object);
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//        super.deleteById(aLong);
//    }
}
