package org.moloshnikov.userdataservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private static final String PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[0-9]).+$";

    @Id
    @Column(name = "login", nullable = false)
    @NotBlank
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    @ManyToMany
    @NotNull
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_login")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
