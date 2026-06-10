package com.acme.kampo.platform.season.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import com.acme.kampo.platform.season.infrastructure.persistence.jpa.entities.SeasonPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonJpaRepository extends JpaRepository<SeasonPersistenceEntity, Long> {

    List<SeasonPersistenceEntity> findAllByFieldId(Long fieldId);

    Optional<SeasonPersistenceEntity> findByFieldIdAndStatusNot(Long fieldId, SeasonStatus status);
}