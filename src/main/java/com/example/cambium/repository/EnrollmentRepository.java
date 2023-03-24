package com.example.cambium.repository;

import com.example.cambium.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Enrollment save(Enrollment enrollment);
}
