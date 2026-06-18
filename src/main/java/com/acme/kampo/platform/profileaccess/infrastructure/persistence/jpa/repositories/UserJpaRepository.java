package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.UserPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserPersistenceEntity, Long> {

    @Query("select u from UserPersistenceEntity u where u.email = :email")
    Optional<UserPersistenceEntity> findByEmail(@Param("email") String email);

    @Query("select count(u) from UserPersistenceEntity u where u.email = :email")
    long countByEmail(@Param("email") String email);
}