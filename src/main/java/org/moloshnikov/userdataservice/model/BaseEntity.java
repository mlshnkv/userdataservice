package org.moloshnikov.userdataservice.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class BaseEntity {
    @NotBlank
//    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    public BaseEntity(@NotBlank String name) {
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
