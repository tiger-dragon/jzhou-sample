package com.example.cambium;

import com.example.cambium.DTO.PersonDTO;
import com.example.cambium.entity.Person;
import com.example.cambium.repository.PersonRepository;
import com.example.cambium.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    // inject mock PersonRepository into PersonService
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person();
        person1.setPersonId(UUID.randomUUID());
        person1.setFirstName("John");
        person1.setLastName("Doe");

        Person person2 = new Person();
        person2.setPersonId(UUID.randomUUID());
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        List<Person> persons = Arrays.asList(person1, person2);

        when(personRepository.findAllByOrderByLastNameAsc()).thenReturn(persons);

        List<PersonDTO> result = personService.getAllPersonsSortedByLastName().get();


        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Doe", result.get(1).getLastName());
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setPersonId(UUID.randomUUID());
        person.setFirstName("John");
        person.setLastName("Doe");

        PersonDTO dto = new PersonDTO();
        dto.setLastName(person.getLastName());
        dto.setFirstName(person.getFirstName());
        dto.setPersonId(person.getPersonId());

        // the JPA repository save function will return the saved object
        when(personRepository.save(any(Person.class))).thenReturn(person);

        // Test saveperson(...) method
        PersonDTO saved = personService.savePerson(dto);

        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
    }

}