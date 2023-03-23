package com.example.cambium.DTO;

import com.example.cambium.Exception.ValidationFailedException;

import java.util.UUID;

public class PersonDTO {
    private Long id;
    private UUID personId;
    private String firstName;
    private String lastName;

    public void validate() throws ValidationFailedException {
        // validate and sanitize...

        // make sure first name and last name is not null
        if (lastName == null || firstName == null )
        {
            throw new ValidationFailedException("name field can't be null");
        }

        // check if name contains invalid characters etc
        if (lastName.matches(".*[%@$#&].*") || firstName.matches(".*[%@$#&].*")) {
            throw new ValidationFailedException("special characters not allowed in name");
        }

        // trim off whitespace
        setFirstName(firstName.trim());
        setLastName(lastName.trim());

        // After trimming, they could become empty string so check it.
        if (lastName.isEmpty() || firstName.isEmpty())
        {
            throw new ValidationFailedException("name field can't be empty");
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
