package com.example.cambium.DTO;

import com.example.cambium.Exception.ValidationFailedException;

import java.util.UUID;

public class PersonDTO {
    private Long id;
    private UUID personId;
    private String firstName;
    private String lastName;

    public void validate() throws ValidationFailedException {
        // validate...
        // for example, check if name and personId contain invalid characters or if they are null, etc
        if (this.lastName.matches(".*[%@$#&].*")) {
            throw new ValidationFailedException("bad character in last name");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
