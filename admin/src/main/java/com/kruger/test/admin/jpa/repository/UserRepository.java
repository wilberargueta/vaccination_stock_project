package com.kruger.test.admin.jpa.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.kruger.test.admin.jpa.entity.UserEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends IRepository<UserEntity> {

    Optional<UserEntity> findByUsernameAndDelete(String username, Boolean delete);
    Optional<UserEntity> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.password =:password WHERE u.id=:id")
    int changePassword(@Param("password") String password,@Param("id") Long id);
}
