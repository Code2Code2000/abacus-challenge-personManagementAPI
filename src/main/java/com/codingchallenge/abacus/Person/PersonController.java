package com.codingchallenge.abacus.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons/data")
public class PersonController {


    @Autowired
    private PersonService personService;

    @PutMapping
    public ResponseEntity<Person> createUpdatePerson(@RequestBody Person person){
        return new ResponseEntity(personService.addPerson(person), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> getAllPerson() {

        List<Person> returnList = personService.getAllPersons();
        return new ResponseEntity(returnList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        return new ResponseEntity(personService.findPerson(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable String id) {
        return new ResponseEntity(personService.deletePerson(id), HttpStatus.OK);
    }


}
