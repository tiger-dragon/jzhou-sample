package com.example.cambium.controller;


import com.example.cambium.DTO.PersonDTO;
import com.example.cambium.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    private PersonService personService;

    // passed
    @GetMapping("/persons/{id}")
    @PreAuthorize("hasAnyRole('USER', 'SUPER_USER')")
    public ResponseEntity<PersonDTO> getPersonByPersonId(@PathVariable UUID id) {
        Optional<PersonDTO> optionalPersonDTO = personService.getPersonByPersonId(id);

        if (optionalPersonDTO.isPresent()) {
            PersonDTO personDTO = optionalPersonDTO.get();
            return new ResponseEntity<>(personDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // passed
    @PostMapping("/persons")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<PersonDTO> savePerson(@RequestBody PersonDTO personDTO) {

        // Validate the request. If validation fails it will throw ValidationFailedException which
        // will be caught by the ExceptionHandlingController and return corresponding error to the caller.
        // Validation is important before inserting the data to the DB.
        personDTO.validate();

        // Note: we have put unique constraints in the DB on the combination of firstName, lastName. So if
        // we get an identical firstName, lastName combination, an exception will occur at the DB level which will
        // be caught by the PersonService and an InsertionFailedException will be thrown. The InsertionFailedException
        // will then be handled by the ExceptionHandlingController and return error message to the caller.
        PersonDTO savedPersonDTO = personService.savePerson(personDTO);

        return new ResponseEntity<>(savedPersonDTO, HttpStatus.OK);

    }

    // passed
    @GetMapping("/persons")
    @PreAuthorize("hasAnyRole('USER', 'SUPER_USER')")
    public ResponseEntity<List<PersonDTO>> getAllPersonsSortedByLastName() {
        Optional<List<PersonDTO>> optionalPersons = personService.getAllPersonsSortedByLastName();
        if (optionalPersons.isPresent()) {
            return ResponseEntity.ok(optionalPersons.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // passed
    @DeleteMapping("/persons/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePersonByPersonId(@PathVariable UUID id) {
        if (personService.existsByPersonId(id)) {
            personService.deleteByPersonId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
