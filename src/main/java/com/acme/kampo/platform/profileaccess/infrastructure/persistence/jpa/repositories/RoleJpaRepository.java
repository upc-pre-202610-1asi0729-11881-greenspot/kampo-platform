package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.RolePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RolePersistenceEntity, Long> {

    @Query("select r from RolePersistenceEntity r where r.position = :position")
    Optional<RolePersistenceEntity> findByPosition(@Param("position") RolePosition position);

    @Query("select count(r) from RolePersistenceEntity r where r.position = :position")
    long countByPosition(@Param("position") RolePosition position);
}