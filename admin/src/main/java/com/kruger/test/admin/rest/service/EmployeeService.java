package com.kruger.test.admin.rest.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.jpa.repository.EmployeeRepository;
import com.kruger.test.admin.rest.model.Employee;
import com.kruger.test.admin.util.convert.EmployeeConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<Employee, EmployeeEntity, EmployeeConvert, EmployeeRepository> {

    public EmployeeService(EmployeeConvert convert, EmployeeRepository repository) {
        super(convert, repository);

    }

    public List<Employee> getByTypeVaccination(String type) {
        if (type == null)
            throw new BadRequestsException("No se envia parametro de consulta");
        return this.repository.findByTypeVaccination(type, false).stream()
                .map(employee -> this.convert.entityToModel(employee)).collect(Collectors.toList());
    }

    public List<Employee> getByStateVaccination(Boolean state) {
        if (state == null)
            throw new BadRequestsException("No se envia parametro de consulta");

        return this.repository.findByVaccinatedAndDelete(state, false).stream()
                .map(employee -> this.convert.entityToModel(employee)).collect(Collectors.toList());
    }

    public List<Employee> getByDateVaccination(String start, String end) {
        if (start == null || end == null)
            throw new BadRequestsException("No se envia parametro de consulta");
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return this.repository.findByDateVaccination(startDate, endDate, false).stream()
                .map(employee -> this.convert.entityToModel(employee)).collect(Collectors.toList());
    }

    public void deleteAllEmployees(List<Employee> employees) {
        employees.forEach(employee -> this.delete(employee.getId()));
    }

    public Boolean validateDocumentNumber(String documentNumber) {
        if (documentNumber == null)
            throw new BadRequestsException("No se envia parametro de consulta");
        return this.repository.findByDocumentNumber(documentNumber).isPresent();
    }
}
