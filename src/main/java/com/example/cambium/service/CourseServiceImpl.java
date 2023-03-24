package com.example.cambium.service;

import com.example.cambium.DTO.CourseDTO;
import com.example.cambium.Exception.InsertionFailedException;
import com.example.cambium.entity.Course;
import com.example.cambium.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    // Save a course
    @Transactional
    @Override
    public CourseDTO save(CourseDTO courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());

        try {
            course = courseRepository.save(course);
        } catch (DataAccessException e) {
            logger.error("insert failed for course with name " + courseDto.getName());
            throw new InsertionFailedException("failed saving course to DB");
        }

        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }


    // Get all course enrolled by a person
    @Transactional
    @Override
    public Optional<List<CourseDTO>> getAllCourses(Long personPrimaryKey) {
        List<Course> courseList = courseRepository.findByEnrollmentsPersonId(personPrimaryKey);
        if ( courseList.isEmpty()) {
            return Optional.empty();
        } else {
            List<CourseDTO> dtoList = courseList.stream().map(course -> courseToCourseDTO(course))
                    .collect(Collectors.toList());
            return Optional.of(dtoList);
        }

    }

    // Create a CourseDTO from a Course
    private CourseDTO courseToCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setName(course.getName());
        dto.setId(course.getId());
        return dto;
    }
}
