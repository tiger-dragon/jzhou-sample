package com.example.cambium.service;

import com.example.cambium.DTO.PersonDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    PersonDTO savePerson(PersonDTO personDTO);

    Optional<PersonDTO> getPersonByPersonId(UUID id);

    Optional<List<PersonDTO>>  getAllPersonsSortedByLastName();
    boolean existsByPersonId(UUID personId);
    void deleteByPersonId(UUID personId);
}
