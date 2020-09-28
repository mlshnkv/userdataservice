package org.moloshnikov.userdataservice.service;

import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.moloshnikov.userdataservice.util.ValidationUtil.assureIdConsistent;
import static org.moloshnikov.userdataservice.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role create(Role role) {
        Assert.notNull(role, "role must not be null");
        return repository.save(role);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, Integer.toString(id));
    }

    public Role get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), Integer.toString(id));
    }

    public List<Role> getAll() {
        return repository.findAll();
    }

    public void update(Role role, int id) {
        Assert.notNull(role, "user must not be null");
        assureIdConsistent(role, id);
        checkNotFoundWithId(repository.save(role), role.getId().toString());
    }
}
