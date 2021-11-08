package com.kruger.test.admin.rest.controller;

import java.util.List;

import com.kruger.test.admin.jpa.entity.VaccinationStockEntity;
import com.kruger.test.admin.jpa.repository.VaccinationStockRepository;
import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.rest.model.VaccinationStock;
import com.kruger.test.admin.rest.service.VaccinationStockService;
import com.kruger.test.admin.util.convert.VaccinationStockConvert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vaccination-stock")
public class VaccinationStockController extends
        AbstractController<VaccinationStock, VaccinationStockEntity, VaccinationStockConvert, VaccinationStockRepository, VaccinationStockService> {

    public VaccinationStockController(VaccinationStockService service) {
        super(service);
    }

    @GetMapping(path = "/{id}/employee")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    public ResponseEntity<Response<List<VaccinationStock>>> getByEmployee(@PathVariable Long id) {

        return new ResponseEntity<Response<List<VaccinationStock>>>(
                new Response<List<VaccinationStock>>(this.service.getByEmployee(id)), HttpStatus.OK);
    }

}
