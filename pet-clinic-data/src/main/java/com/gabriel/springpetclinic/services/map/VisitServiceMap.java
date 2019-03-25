package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.Visit;
import com.gabriel.springpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {
}
