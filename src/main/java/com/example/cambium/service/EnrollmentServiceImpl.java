package com.example.cambium.service;

import com.example.cambium.DTO.EnrollmentDTO;
import com.example.cambium.Exception.InsertionFailedException;
import com.example.cambium.Exception.ValidationFailedException;
import com.example.cambium.entity.Course;
import com.example.cambium.entity.Enrollment;
import com.example.cambium.entity.Person;
import com.example.cambium.repository.CourseRepository;
import com.example.cambium.repository.EnrollmentRepository;
import com.example.cambium.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    @Override
    public EnrollmentDTO save(EnrollmentDTO dto) {
        // enrollment is always associated with existing Person and Course, so
        // we need to retrieve them to create the association.
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ValidationFailedException("Invalid person ID"));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ValidationFailedException("Invalid course ID"));

        // create enrollment and add the relationship
        Enrollment enrollment = new Enrollment();
        enrollment.setPerson(person);
        enrollment.setCourse(course);

        // save the enrollment
        try {
            enrollment = enrollmentRepository.save(enrollment);
        } catch (DataAccessException e) {
            logger.error("insert failed for enrollment with course " + dto.getCourseId()
            + " and person " + dto.getPersonId());
            throw new InsertionFailedException("failed saving enrollment to DB");
        }

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getId(), enrollment.getPerson().getId(),
                enrollment.getCourse().getId());
        return enrollmentDTO;
    }

}
