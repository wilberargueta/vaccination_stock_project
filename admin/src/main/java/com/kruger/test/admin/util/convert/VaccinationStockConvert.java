package com.kruger.test.admin.util.convert;

import com.kruger.test.admin.jpa.entity.VaccinationStockEntity;
import com.kruger.test.admin.rest.model.VaccinationStock;

import org.springframework.stereotype.Component;

@Component
public class VaccinationStockConvert extends AbstractConvert<VaccinationStock, VaccinationStockEntity> {

    private final EmployeeConvert employeeConvert;

    public VaccinationStockConvert(EmployeeConvert employeeConvert) {
        this.employeeConvert = employeeConvert;
    }

    @Override
    public VaccinationStock entityToModel(VaccinationStockEntity entity, Boolean eager) {
        VaccinationStock model = new VaccinationStock();
        model.setDate(entity.getDate());
        model.setDose(entity.getDose());
        model.setId(entity.getId());
        model.setType(entity.getType());
        if (eager && entity.getEmployee() != null)
            model.setEmployee(this.employeeConvert.entityToModel(entity.getEmployee()));
        return model;
    }

    @Override
    public VaccinationStockEntity modelToEntity(VaccinationStock model, Boolean eager) {
        VaccinationStockEntity entity = new VaccinationStockEntity();
        entity.setDate(model.getDate());
        entity.setDose(model.getDose());
        entity.setId(model.getId());
        entity.setType(model.getType());
        if (eager && model.getEmployee() != null)
            entity.setEmployee(this.employeeConvert.modelToEntity(model.getEmployee()));
        return entity;
    }

}
