package com.kruger.test.admin.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.jpa.entity.VaccinationStockEntity;
import com.kruger.test.admin.jpa.repository.VaccinationStockRepository;
import com.kruger.test.admin.rest.model.VaccinationStock;
import com.kruger.test.admin.util.convert.VaccinationStockConvert;
import com.kruger.test.admin.util.execption.BadRequestsException;

import org.springframework.stereotype.Service;

@Service
public class VaccinationStockService extends
        AbstractService<VaccinationStock, VaccinationStockEntity, VaccinationStockConvert, VaccinationStockRepository> {

    public VaccinationStockService(VaccinationStockConvert convert, VaccinationStockRepository repository) {
        super(convert, repository);
    }

    public List<VaccinationStock> getByEmployee(Long employeeId) {
        if (employeeId == null)
            throw new BadRequestsException("Debe enviar el Id del empleado en la peticion");
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        return this.repository.findByEmployeeAndDelete(employeeEntity, false).stream()
                .map(vaccination -> this.convert.entityToModel(vaccination)).collect(Collectors.toList());
    }
    

}
