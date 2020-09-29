package org.moloshnikov.userdataservice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class BaseEntity {
    public static final String NAME_NOTICE = "The name must not be empty";

    @NotBlank(message = NAME_NOTICE)
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