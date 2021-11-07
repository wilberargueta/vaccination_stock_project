package com.kruger.test.admin.rest.controller;

import java.util.List;

import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.model.Role;
import com.kruger.test.admin.rest.service.RoleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Response<Role>> save(@RequestBody Role role) {

        return new ResponseEntity<Response<Role>>(new Response<Role>(this.roleService.save(role)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Response<Role>> update(@RequestBody Role role) {

        return new ResponseEntity<Response<Role>>(new Response<Role>(this.roleService.update(role)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<Role>> delete(@PathVariable Long id) {
        this.roleService.delete(id);
        return new ResponseEntity<Response<Role>>(new Response<Role>(null), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<Role>> getById(@PathVariable Long id) {

        return new ResponseEntity<Response<Role>>(new Response<Role>(this.roleService.getById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAll() {

        return new ResponseEntity<Response<List<Role>>>(new Response<List<Role>>(this.roleService.getAll()),
                HttpStatus.OK);
    }
}
