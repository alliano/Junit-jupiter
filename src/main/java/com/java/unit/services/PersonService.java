package com.java.unit.services;

import java.util.UUID;

import com.java.unit.models.Person;
import com.java.unit.repositories.PersonRepository;

public class PersonService {
    
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(String id) {
        Person person = this.personRepository.findByPersonId(id);
        if(person != null) {
            return person;
        }
        else {
            throw new IllegalArgumentException("Person not found");
        }
    }

    public Person register(String name) {
        Person person = new Person(UUID.randomUUID().toString(), name);
        this.personRepository.insert(person);
        return person;
    }
}
