package org.moloshnikov.userdataservice.web;

import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.service.UserService;
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
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String REST_URL = "/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{login}")
    public User get(@PathVariable String login) {
        return userService.get(login);
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String login) {
        userService.delete(login);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Info> createWithLocation(@RequestBody @Valid User user) {
        User created = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{login}")
                .buildAndExpand(created.getLogin()).toUri();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        return new ResponseEntity<>(new Info(true), responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Info> update(@RequestBody @Valid User user, @PathVariable String login) {
        userService.update(user, login);
        return new ResponseEntity<>(new Info(true), HttpStatus.OK);
    }
}