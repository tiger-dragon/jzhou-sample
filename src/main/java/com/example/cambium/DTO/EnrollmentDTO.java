package com.example.cambium.DTO;

public class EnrollmentDTO {

    private Long id;
    private Long personId;
    private Long courseId;

    public EnrollmentDTO(Long id, Long personId, Long courseId) {
        this.id = id;
        this.personId = personId;
        this.courseId = courseId;
    }

    public EnrollmentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}
