package org.moloshnikov.userdataservice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseEntity {
    @NotBlank(message = "The name must not be empty")
    @Size(min = 2, max = 255, message = "Name is too short or too long")
    @Column(name = "name", nullable = false)
    private String name;

    public BaseEntity(String name) {
        this.name = name;
    }

    public BaseEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}