package com.example.cambium.repository;

import com.example.cambium.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person save(Person person);
    Optional<Person> findByPersonId(UUID personId);
    List<Person> findAllByOrderByLastNameAsc();
    boolean existsByPersonId(UUID personId);
    void deleteByPersonId(UUID personId);
}
