package com.gabriel.springpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T, ID>{


    protected Map<ID, T> map = new HashMap<>();

    T save(ID id, T object) {
        return map.put(id, object);
    }

    T findById(ID id) {
        return map.get(id);
    }

    public Set findAll() {
        return new HashSet<>(map.values());
    }


    public void delete(T object) {
        map.entrySet().removeIf(entry-> entry.getValue().equals(object));
    }


    public void deleteById(ID id) {
        map.remove(id);
    }

}
