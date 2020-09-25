package org.moloshnikov.userdataservice.service;

import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role create(Role role) {
        Assert.notNull(role, "user must not be null");
        return repository.save(role);
    }

    public void delete(int id) {
        repository.deleteById(id);
//        checkNotFoundWithId(repository.delete(id), id);
    }

    public Role get(int id) {
        return repository.findById(id).orElse(null);
//        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Role> getAll() {
        return repository.findAll();
    }

    public void update(Role role) {
        Assert.notNull(role, "user must not be null");
        repository.save(role);
    }
}
