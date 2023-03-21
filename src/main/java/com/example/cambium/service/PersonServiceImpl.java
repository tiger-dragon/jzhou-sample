package com.example.cambium.service;

import com.example.cambium.DTO.PersonDTO;
import com.example.cambium.Exception.InsertionFailedException;
import com.example.cambium.controller.PersonController;
import com.example.cambium.entity.Person;
import com.example.cambium.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    @Override
    public PersonDTO savePerson(PersonDTO personDTO)  throws InsertionFailedException {
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());

        try {
            person = personRepository.save(person);
        } catch (DataAccessException e) {
            logger.error("insert failed for person with id " + personDTO.getPersonId());
            throw new InsertionFailedException("failed saving person to DB");
        }

        personDTO.setId(person.getId());
        personDTO.setPersonId(person.getPersonId());

        return personDTO;
    }

    @Transactional
    @Override
    public Optional<PersonDTO> getPersonByPersonId(UUID id) {
        Optional<Person> optionalPerson = personRepository.findByPersonId(id);

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(person.getId());
            personDTO.setPersonId(person.getPersonId());
            personDTO.setFirstName(person.getFirstName());
            personDTO.setLastName(person.getLastName());

            return Optional.of(personDTO);
        } else {
            return Optional.empty();
        }
    }


    @Transactional
    @Override
    public Optional<List<PersonDTO>> getAllPersonsSortedByLastName() {
        List<Person> persons = personRepository.findAllByOrderByLastNameAsc();
        if (persons.isEmpty()) {
            return Optional.empty();
        }
        List<PersonDTO> listDTO = persons.stream()
                .map(person -> personToPersonDTO(person))
                .collect(Collectors.toList());
        return Optional.of(listDTO);
    }

    private PersonDTO personToPersonDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setPersonId(person.getPersonId());
        return dto;
    }

    @Transactional
    @Override
    public boolean existsByPersonId(UUID personId) {
        return personRepository.existsByPersonId(personId);
    }

    @Transactional
    @Override
    public void deleteByPersonId(UUID personId) {
        personRepository.deleteByPersonId(personId);
    }
}
