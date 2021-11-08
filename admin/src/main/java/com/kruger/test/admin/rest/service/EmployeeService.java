package com.kruger.test.admin.rest.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.jpa.repository.EmployeeRepository;
import com.kruger.test.admin.rest.model.Employee;
import com.kruger.test.admin.rest.model.User;
import com.kruger.test.admin.util.convert.EmployeeConvert;
import com.kruger.test.admin.util.convert.UserConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;
import com.kruger.test.admin.util.execption.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<Employee, EmployeeEntity, EmployeeConvert, EmployeeRepository> {

    @Autowired
    private UserConvert userConvert;

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

    public void updateStateVaccinated(Boolean vaccinated, Long id) {
        if (vaccinated == null)
            throw new BadRequestsException("No se envia parametro de consulta");

        this.repository.updateStateVaccinate(vaccinated, id);
    }

    public Employee getByUserAccess(User userAccess) {
        if (userAccess == null)
            throw new BadRequestsException("No se envia parametro de consulta");
        Optional<EmployeeEntity> optional = this.repository
                .findByUserAccess(this.userConvert.modelToEntity(userAccess));
        if (optional.isEmpty())
            throw new ObjectNotFoundException("Registro no encontrado");
        return this.convert.entityToModel(optional.get(), true);

    }
}
