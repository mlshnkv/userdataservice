package org.moloshnikov.userdataservice.web;

import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.service.RoleService;
import org.moloshnikov.userdataservice.util.exception.Info;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RoleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
    static final String REST_URL = "/roles";
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public Role get(@PathVariable int id) {
        return roleService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        roleService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Info> create(@RequestBody @Valid Role role) {
        Role created = roleService.create(role);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(new Info(true));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Valid Role role, @PathVariable int id) {
        roleService.update(role, id);
    }
}