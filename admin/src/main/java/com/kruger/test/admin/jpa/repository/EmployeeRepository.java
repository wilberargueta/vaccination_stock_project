package com.kruger.test.admin.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.kruger.test.admin.jpa.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends IRepository<EmployeeEntity> {
    Optional<EmployeeEntity> findByIdAndDelete(Long id, Boolean delete);

    Optional<EmployeeEntity> findByDocumentNumber(String documentNumber);

    List<EmployeeEntity> findByDelete(Boolean delete);

    List<EmployeeEntity> findByVaccinatedAndDelete(Boolean vaccinated, Boolean delete);

    @Query("SELECT e FROM EmployeeEntity e INNER JOIN e.vaccinationStock vs WHERE vs.type=:type AND e.delete =:delete")
    List<EmployeeEntity> findByTypeVaccination(@Param("type") String typeVaccination, @Param("delete") Boolean delete);

    @Query("SELECT e FROM EmployeeEntity e INNER JOIN e.vaccinationStock vs WHERE vs.date BETWEEN :start AND :end AND e.delete =:delete")
    List<EmployeeEntity> findByDateVaccination(@Param("start") LocalDate start, @Param("end") LocalDate end,
            @Param("delete") Boolean delete);
}
