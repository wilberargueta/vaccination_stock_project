package com.kruger.test.admin.util.convert;

import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.rest.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConvert extends AbstractConvert<Employee, EmployeeEntity> {
 @Autowired
    private VaccinationStockConvert vaccinationStockConvert;
    
    @Autowired
    UserConvert userConvert;
    @Override
    public Employee entityToModel(EmployeeEntity entity, Boolean eager) {
        Employee model = new Employee();
        model.setAddress(entity.getAddress());
        model.setBirthday(entity.getBirthday());
        model.setDelete(entity.getDelete());
        model.setDocumentNumber(entity.getDocumentNumber());
        model.setEmail(entity.getEmail());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setId(entity.getId());
        model.setPhone(entity.getPhone());
        model.setVaccinated(entity.getVaccinated());

        if (eager && entity.getUserAccess() != null)
            model.setUserAccess(this.userConvert.entityToModel(entity.getUserAccess()));

        if (eager && entity.getVaccinationStock() != null)
            model.setVaccinationStock(entity.getVaccinationStock().stream()
                    .map(vs -> this.vaccinationStockConvert.entityToModel(vs)).collect(Collectors.toList()));

        return model;
    }

    @Override
    public EmployeeEntity modelToEntity(Employee model, Boolean eager) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setAddress(model.getAddress());
        entity.setBirthday(model.getBirthday());
        entity.setDelete(model.getDelete());
        entity.setDocumentNumber(model.getDocumentNumber());
        entity.setEmail(model.getEmail());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setId(model.getId());
        entity.setPhone(model.getPhone());
        entity.setVaccinated(model.getVaccinated());

        if (eager && model.getUserAccess() != null)
            entity.setUserAccess(this.userConvert.modelToEntity(model.getUserAccess()));

        if (eager && model.getVaccinationStock() != null)
            entity.setVaccinationStock(model.getVaccinationStock().stream()
                    .map(vs -> this.vaccinationStockConvert.modelToEntity(vs)).collect(Collectors.toList()));

        return entity;
    }

}
