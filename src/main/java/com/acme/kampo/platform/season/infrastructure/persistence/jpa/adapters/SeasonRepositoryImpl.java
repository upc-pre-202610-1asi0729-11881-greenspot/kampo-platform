package com.acme.kampo.platform.season.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import com.acme.kampo.platform.season.domain.repository.SeasonRepository;
import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.season.infrastructure.persistence.jpa.assemblers.SeasonPersistenceAssembler;
import com.acme.kampo.platform.season.infrastructure.persistence.jpa.repositories.SeasonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeasonRepositoryImpl implements SeasonRepository {

    private final SeasonJpaRepository seasonJpaRepository;

    public SeasonRepositoryImpl(SeasonJpaRepository seasonJpaRepository) {
        this.seasonJpaRepository = seasonJpaRepository;
    }

    @Override
    public Optional<Season> findById(SeasonId seasonId) {
        return seasonJpaRepository.findById(seasonId.getValue())
                .map(SeasonPersistenceAssembler::toDomain);
    }

    @Override
    public List<Season> findByFieldId(FieldId fieldId) {
        return seasonJpaRepository.findAllByFieldId(fieldId.getValue())
                .stream()
                .map(SeasonPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public Optional<Season> findActiveSeasonByFieldId(FieldId fieldId) {
        return seasonJpaRepository.findByFieldIdAndStatusNot(fieldId.getValue(), SeasonStatus.ENDED)
                .map(SeasonPersistenceAssembler::toDomain);
    }

    @Override
    public Season save(Season season) {
        var entity = SeasonPersistenceAssembler.toEntity(season);
        var saved = seasonJpaRepository.save(entity);
        return SeasonPersistenceAssembler.toDomain(saved);
    }

    @Override
    public void delete(SeasonId seasonId) {
        seasonJpaRepository.deleteById(seasonId.getValue());
    }
}