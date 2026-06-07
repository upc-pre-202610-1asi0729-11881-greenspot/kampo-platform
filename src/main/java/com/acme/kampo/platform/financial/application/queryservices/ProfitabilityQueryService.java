package com.acme.kampo.platform.financial.application.queryservices;

import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityByFundoQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityBySeasonQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityHistoryQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Profitability} read operations.
 */
public interface ProfitabilityQueryService {

    Optional<Profitability> handle(GetProfitabilityByFundoQuery query);

    Optional<Profitability> handle(GetProfitabilityBySeasonQuery query);

    List<Profitability> handle(GetProfitabilityHistoryQuery query);
}
