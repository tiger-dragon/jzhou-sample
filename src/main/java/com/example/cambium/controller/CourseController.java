package com.example.cambium.controller;

import com.example.cambium.DTO.CourseDTO;
import com.example.cambium.service.CourseService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Add a course to the DB
    // The format of the JSON object is
    // {
    //    "name": "Mathematics"
    // }
    @PostMapping("/courses")
    @PreAuthorize("hasRole('SUPER_USER')")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO) {

        CourseDTO savedCourse = courseService.save(courseDTO);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

}
