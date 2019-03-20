package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.PetType;
import com.gabriel.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetTypeServiceMap extends AbstractMapService<PetType, Long> implements PetTypeService {

}
