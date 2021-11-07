package com.kruger.test.admin.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kruger.test.admin.jpa.entity.AbstractAuditEntity;
import com.kruger.test.admin.jpa.repository.IRepository;
import com.kruger.test.admin.rest.model.IModel;
import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.service.AbstractService;
import com.kruger.test.admin.util.convert.AbstractConvert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractController<M extends IModel, E extends AbstractAuditEntity, C extends AbstractConvert<M, E>, R extends IRepository<E>, S extends AbstractService<M, E, C, R>> {

    protected final S service;
    protected final Map<String, String> authority = new HashMap<>();
    String autorString;
    public AbstractController(S service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<M>> save(@RequestBody M model) {

        return new ResponseEntity<Response<M>>(new Response<M>(this.service.save(model)), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<M>> update(@RequestBody M model) {

        return new ResponseEntity<Response<M>>(new Response<M>(this.service.update(model)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<M>> delete(@PathVariable Long id) {
        this.service.delete(id);
        return new ResponseEntity<Response<M>>(new Response<M>(null), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<M>> getById(@PathVariable Long id) {

        return new ResponseEntity<Response<M>>(new Response<M>(this.service.getById(id)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<M>>> getAll() {

        return new ResponseEntity<Response<List<M>>>(new Response<List<M>>(this.service.getAll()), HttpStatus.OK);
    }
}
