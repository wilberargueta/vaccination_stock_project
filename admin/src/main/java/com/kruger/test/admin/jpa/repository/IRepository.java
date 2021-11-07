package com.kruger.test.admin.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface IRepository<E> extends JpaRepository<E, Long> {
    Optional<E> findByIdAndDelete(Long id, Boolean delete);

    List<E> findByDelete(Boolean delete);
}
