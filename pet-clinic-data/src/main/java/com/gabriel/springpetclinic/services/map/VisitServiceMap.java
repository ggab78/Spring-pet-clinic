package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Visit save(Visit object) {

        if(object.getPet()==null || object.getPet().getOwner() == null || object.getPet().getId()==null
        || object.getPet().getOwner().getId()==null){
            throw new RuntimeException("Invalid visit");
        }else {
            return super.save(object);
        }
    }
}
