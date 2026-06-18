package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.UserRolePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRolePersistenceEntity, Long> {

    @Query("select ur from UserRolePersistenceEntity ur where ur.userId = :userId")
    List<UserRolePersistenceEntity> findByUserId(@Param("userId") Long userId);

    @Query("select ur from UserRolePersistenceEntity ur where ur.roleId = :roleId")
    List<UserRolePersistenceEntity> findByRoleId(@Param("roleId") Long roleId);

    @Query("select count(ur) from UserRolePersistenceEntity ur where ur.userId = :userId and ur.roleId = :roleId")
    long countByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);
}