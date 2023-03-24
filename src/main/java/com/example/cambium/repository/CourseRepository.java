package com.example.cambium.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cambium.entity.Course;
import java.util.List;


public interface CourseRepository extends JpaRepository<Course, Long> {
    Course save(Course course);

    // pure Spring Data black magic using naming convention to define query!
    List<Course> findByEnrollmentsPersonId(Long personId);
}
