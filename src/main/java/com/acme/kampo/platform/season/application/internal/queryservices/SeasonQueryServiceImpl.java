package com.acme.kampo.platform.season.application.internal.queryservices;

import com.acme.kampo.platform.season.application.queryservices.SeasonQueryService;
import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.queries.GetActiveSeasonByFieldIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonByIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonsByFieldIdQuery;
import com.acme.kampo.platform.season.domain.repository.SeasonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SeasonQueryServiceImpl implements SeasonQueryService {

    private final SeasonRepository seasonRepository;

    public SeasonQueryServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Optional<Season> handle(GetSeasonByIdQuery query) {
        log.debug("Querying season by id={}", query.seasonId().getValue());
        return seasonRepository.findById(query.seasonId());
    }

    @Override
    public List<Season> handle(GetSeasonsByFieldIdQuery query) {
        log.debug("Querying seasons by fieldId={}", query.fieldId().getValue());
        return seasonRepository.findByFieldId(query.fieldId());
    }

    @Override
    public Optional<Season> handle(GetActiveSeasonByFieldIdQuery query) {
        log.debug("Querying active season by fieldId={}", query.fieldId().getValue());
        return seasonRepository.findActiveSeasonByFieldId(query.fieldId());
    }
}