package org.moloshnikov.userdataservice.service;

import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.repository.UserRepository;
import org.moloshnikov.userdataservice.util.exception.IllegalRequestDataException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.moloshnikov.userdataservice.util.ValidationUtil.*;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        if (repository.getByLogin(user.getLogin()) != null) {
            throw new IllegalRequestDataException("User with login: " + user.getLogin() + " already exist.");
        }
        return repository.save(user);
    }

    public void delete(String login) {
        checkNotFound(repository.deleteByLogin(login) != 0, login);
    }

    public User get(String login) {
        return checkNotFoundWithId(repository.getByLogin(login), login);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void update(User user, String login) {
        Assert.notNull(user, "user must not be null");
        assureLoginConsistent(user, login);
        checkNotFoundWithId(repository.save(user), user.getLogin());
    }
}