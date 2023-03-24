package com.example.cambium.service;

import com.example.cambium.DTO.CourseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    CourseDTO save(CourseDTO courseDto);
    Optional<List<CourseDTO>> getAllCourses(Long personPrimaryKey);
}
