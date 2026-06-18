package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.RolePermissionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionPersistenceEntity, Long> {

    @Query("select rp from RolePermissionPersistenceEntity rp where rp.roleId = :roleId")
    List<RolePermissionPersistenceEntity> findByRoleId(@Param("roleId") Long roleId);

    @Query("select count(rp) from RolePermissionPersistenceEntity rp where rp.roleId = :roleId and rp.permissionId = :permissionId")
    long countByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}