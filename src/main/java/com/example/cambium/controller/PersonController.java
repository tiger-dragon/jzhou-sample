package com.example.cambium.controller;


import com.example.cambium.DTO.CourseDTO;
import com.example.cambium.DTO.EnrollmentDTO;
import com.example.cambium.DTO.PersonDTO;
import com.example.cambium.service.CourseService;
import com.example.cambium.service.EnrollmentService;
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

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    // Retrieve a person by its UUID
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


    // Enroll a person in a course. Here the personId is the primary key of the person and
    // the courseId is the primary key of the course.
    @PostMapping("/persons/{personId}/enroll/{courseId}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<EnrollmentDTO> enroll(@PathVariable Long personId,
                                            @PathVariable Long courseId) {

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setPersonId(personId);
        dto.setCourseId(courseId);
        EnrollmentDTO saved = enrollmentService.save(dto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);

    }

    // Find all courses a person enrolled. The personId is the primary key of the person.
    @GetMapping("/persons/{personId}/courses")
    @PreAuthorize("hasAnyRole('SUPER_USER', 'USER')")
    public ResponseEntity<List<CourseDTO>> findAllCoursesByPerson(@PathVariable Long personId /* person primary key */) {
        Optional<List<CourseDTO>> optionalCourseList = courseService.getAllCourses(personId);

        if ( optionalCourseList.isPresent()) {
            return ResponseEntity.ok(optionalCourseList.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

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

        return new ResponseEntity<>(savedPersonDTO, HttpStatus.CREATED);

    }


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


    @DeleteMapping("/persons/{id}")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<Void> deletePersonByPersonId(@PathVariable UUID id) {
        if (personService.existsByPersonId(id)) {
            personService.deleteByPersonId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
