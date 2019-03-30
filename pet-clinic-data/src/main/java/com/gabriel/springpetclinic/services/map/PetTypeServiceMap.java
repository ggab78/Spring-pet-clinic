package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetTypeServiceMap extends AbstractMapService<PetType, Long> implements PetTypeService {

}
