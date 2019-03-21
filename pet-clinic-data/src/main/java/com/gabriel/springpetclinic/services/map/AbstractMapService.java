package com.gabriel.springpetclinic.services.map;

import com.gabriel.springpetclinic.model.BaseEntity;

import java.util.*;

public class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    public T save(T object) {
        if(object!=null){
            if(object.getId()==null){
                object.setId(nextId());
            }
            map.put(object.getId(),object);
        }else{
            throw new RuntimeException("Object cannot be null");
        }
        return object;
    }

    public T findById(ID id) {
        return map.get(id);
    }

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public void delete(T object) {
        map.entrySet().removeIf(entry-> entry.getValue().equals(object));
    }

    public void deleteById(ID id) {
        map.remove(id);
    }

    private Long nextId(){
        Long nextId = null;
        try{
            nextId = Collections.max(map.keySet())+1;
        }catch(NoSuchElementException e){
            nextId=1L;
        }
        return nextId;
    }

}
