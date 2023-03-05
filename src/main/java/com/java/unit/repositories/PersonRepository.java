package com.java.unit.repositories;

import com.java.unit.models.Person;

public interface PersonRepository {
    
    public Person findByPersonId(String id);
    
}
