package org.moloshnikov.userdataservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private static final String PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[0-9]).+$";

    @Id
    @Column(name = "login", nullable = false)
    @NotBlank(message = "The login must not be empty")
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN, message = "Password must contain at least one number or uppercase letter.")
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @NotNull(message = "The user must have at least one role")
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_login")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String login, String password, Set<Role> roles) {
        super(name);
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String login, String password, Role... roles) {
        this(name, login, password, Stream.of(roles).collect(Collectors.toSet()));
    }

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

    public boolean isNew() {
        return getLogin() == null;
    }

    @Override
    public String toString() {
        return "User: name " + getName() + '\'' +
                ", login " + login;
    }
}
