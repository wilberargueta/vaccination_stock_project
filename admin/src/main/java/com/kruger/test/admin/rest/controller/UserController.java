package com.kruger.test.admin.rest.controller;

import java.util.List;

import com.kruger.test.admin.jpa.entity.UserEntity;
import com.kruger.test.admin.jpa.repository.UserRepository;
import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.model.User;
import com.kruger.test.admin.rest.service.UserService;
import com.kruger.test.admin.util.convert.UserConvert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<User, UserEntity, UserConvert, UserRepository, UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @PostMapping(path = "/update-password")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<Boolean>> updatePassword(@RequestBody User user) {
        this.service.updatePassword(user);
        return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(true), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Boolean>> deleteList(@RequestBody List<User> users) {
        this.service.deleteList(users);
        return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(true), HttpStatus.OK);
    }

    @Override
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<User>> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<User>> update(@RequestBody User model) {
        return super.update(model);
    }

    @GetMapping(path = "/{username}/exists-username")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<Boolean>> existsUsername(@PathVariable String username) {
        return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(this.service.usernameValid(username)),
                HttpStatus.OK);
    }

}
