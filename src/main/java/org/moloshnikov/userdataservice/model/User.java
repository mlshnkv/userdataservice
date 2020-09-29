package org.moloshnikov.userdataservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public static final String PASSWORD_NOTICE = "Password must contain one capital letter and one number";
    public static final String PASSWORD_NULLABLE = "Password must not be empty";
    public static final String LOGIN_NOTICE = "The login must not be empty";
    public static final String ROLE_NOTICE = "The user must have at least one role";

    private static final String PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[0-9]).+$";

    @Id
    @Column(name = "login", nullable = false)
    @NotBlank(message = LOGIN_NOTICE)
    private String login;

    @Column(name = "password", nullable = false)
    @NotNull(message = PASSWORD_NULLABLE)
    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_NOTICE)
    private String password;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @NotNull(message = ROLE_NOTICE)
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

    public User(User user) {
        super(user.getName());
        this.login = user.getLogin();
        this.password = user.password;
        this.roles = user.getRoles();
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

    public boolean isNew() {
        return getLogin() == null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'';
    }
}
