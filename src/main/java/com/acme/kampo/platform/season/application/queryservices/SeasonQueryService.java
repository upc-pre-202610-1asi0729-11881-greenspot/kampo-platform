package com.acme.kampo.platform.season.application.queryservices;


import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.queries.GetActiveSeasonByFieldIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonByIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonsByFieldIdQuery;

import java.util.List;
import java.util.Optional;

public interface SeasonQueryService {
    Optional<Season> handle(GetSeasonByIdQuery query);
    List<Season> handle(GetSeasonsByFieldIdQuery query);
    Optional<Season> handle(GetActiveSeasonByFieldIdQuery query);
}
