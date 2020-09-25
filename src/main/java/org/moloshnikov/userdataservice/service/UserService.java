package org.moloshnikov.userdataservice.service;

import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void delete(int id) {
        repository.deleteById(id);
//        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return repository.findById(id).orElse(null);
//        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }
}
