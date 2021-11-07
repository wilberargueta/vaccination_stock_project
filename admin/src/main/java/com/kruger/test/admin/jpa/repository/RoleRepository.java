package com.kruger.test.admin.jpa.repository;

import java.util.List;
import java.util.Optional;

import com.kruger.test.admin.jpa.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByIdAndEnable(Long id, Boolean enable);

    List<RoleEntity> findByEnable(Boolean enable);
}
