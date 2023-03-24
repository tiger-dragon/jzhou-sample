package com.example.cambium.entity;

import javax.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
// In a real-world app, the below unique constraints should be managed in the DB, not in the entity class
// Here we are doing it like this because Spring Boot is automatically creating tables in H2 DB based on
// the entity classes.
@Table(name = "Person", uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERSON_ID", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID personId;

    @Column(name = "FIRST_NAME")
    private String firstName;


    @Column(name = "LAST_NAME")
    private String lastName;

    // a person can have a list of enrollment
    @OneToMany(mappedBy = "person")
    private List<Enrollment> enrollments;

    public Long getId() {
        return id;
    }

    public UUID getPersonId() {
        return personId;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @PrePersist
    private void generatePersonId() {
        if (personId == null) {
            personId = UUID.randomUUID();
        }
    }
}