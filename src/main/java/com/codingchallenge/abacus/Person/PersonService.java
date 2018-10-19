package com.codingchallenge.abacus.Person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public Person deletePerson(String id) {

        Optional<Person> person = personRepository.findById(Long.parseLong(id));

        if (person.isPresent()) {
            Person returnObject = person.get();
            personRepository.deleteById(Long.parseLong(id));
            return returnObject;
        } else {
            throw new EmptyResultDataAccessException("Person with Id " + id + " not found.", 1);
        }
    }


    public Person findPerson(String id) {
        Optional<Person> person = personRepository.findById(Long.parseLong(id));
        if(!person.isPresent()) {
            throw new NoSuchElementException("Person with Id " + id + " not found.");
        }
        return person.get();
    }

    public List<Person> getAllPersons() {
        List<Person>  listOfPerson = new ArrayList<>();
        personRepository.findAll()
                .forEach(listOfPerson::add);
        return listOfPerson;
    }
}
