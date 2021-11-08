package com.kruger.test.admin.rest.controller;

import java.util.List;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.jpa.repository.EmployeeRepository;
import com.kruger.test.admin.rest.model.Employee;
import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.model.User;
import com.kruger.test.admin.rest.service.EmployeeService;
import com.kruger.test.admin.util.convert.EmployeeConvert;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
@Api(value = "Employee", description = "Endpoint para administracion de empleados")
public class EmployeeController
        extends AbstractController<Employee, EmployeeEntity, EmployeeConvert, EmployeeRepository, EmployeeService> {

    public EmployeeController(EmployeeService service) {
        super(service);

    }

    @Override
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @ApiOperation(notes = "Servicio de consulta de empleado", value = "", httpMethod = "GET", consumes = "application/json", produces = "application/json", tags = "getById")
    public ResponseEntity<Response<Employee>> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @GetMapping(path = "/{documentNumber}/validate-document-number")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<Boolean>> validateDocumentNumber(@PathVariable String documentNumber) {
        return new ResponseEntity<Response<Boolean>>(
                new Response<Boolean>(this.service.validateDocumentNumber(documentNumber)), HttpStatus.OK);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<Employee>> update(@RequestBody Employee model) {
        return super.update(model);
    }

    @PostMapping(path = "/delete/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Boolean>> deleteList(@RequestBody List<Employee> employees) {
        this.service.deleteAllEmployees(employees);
        return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(true), HttpStatus.OK);
    }

    @GetMapping(path = "/vaccination/{state}/state")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<Employee>>> getStateVaccination(@PathVariable Boolean state) {

        return new ResponseEntity<Response<List<Employee>>>(
                new Response<List<Employee>>(this.service.getByStateVaccination(state)), HttpStatus.OK);
    }

    @GetMapping(path = "/vaccination/{type}/type")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<Employee>>> getTypeVaccination(@PathVariable String type) {

        return new ResponseEntity<Response<List<Employee>>>(
                new Response<List<Employee>>(this.service.getByTypeVaccination(type)), HttpStatus.OK);
    }

    @GetMapping(path = "/vaccination/{start}/{end}/date")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<Employee>>> getDateVaccination(@PathVariable String start,
            @PathVariable String end) {

        return new ResponseEntity<Response<List<Employee>>>(
                new Response<List<Employee>>(this.service.getByDateVaccination(start, end)), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}/{state}/vaccinated")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Boolean>> updateVaccinated(@PathVariable Long id, @PathVariable Boolean state) {
        this.service.updateStateVaccinated(state, id);
        return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(state), HttpStatus.OK);
    }

    @PostMapping("/by-user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<Employee>> getByUserAccess(@RequestBody User user) {
        return new ResponseEntity<Response<Employee>>(new Response<Employee>(this.service.getByUserAccess(user)),
                HttpStatus.OK);
    }

}
