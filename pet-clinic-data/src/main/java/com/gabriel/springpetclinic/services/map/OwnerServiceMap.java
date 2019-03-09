package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Owner;
import com.gabriel.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;


@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    @Override
    public Owner findByLastName(String lastName) {
        for (Owner o : map.values()) {
            if(o.getLastName().equals(lastName)){
                return o;
            }
        }
        return null;
    }

//    @Override
//    public Owner save(Owner object) {
//        return super.save(object);
//    }
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
