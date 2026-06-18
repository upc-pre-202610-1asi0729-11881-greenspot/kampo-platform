package com.acme.kampo.platform.season.domain.repository;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository {

    Optional<Season> findById(SeasonId seasonId);

    List<Season> findByFieldId(FieldId fieldId);

    Optional<Season> findActiveSeasonByFieldId(FieldId fieldId);

    Season save(Season season);

    void delete(SeasonId seasonId);

}
