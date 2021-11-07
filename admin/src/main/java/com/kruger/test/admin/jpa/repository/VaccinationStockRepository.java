package com.kruger.test.admin.jpa.repository;

import java.util.List;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;
import com.kruger.test.admin.jpa.entity.VaccinationStockEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationStockRepository extends IRepository<VaccinationStockEntity> {

    List<VaccinationStockEntity> findByEmployeeAndDelete(EmployeeEntity employee, Boolean delete);
}
