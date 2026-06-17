package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;

public record GetProfitabilityBySeasonQuery(FundoId fundoId, SeasonId seasonId) {
    public GetProfitabilityBySeasonQuery {
        if (fundoId == null) throw new IllegalArgumentException("fundoId must not be null");
        if (seasonId == null) throw new IllegalArgumentException("seasonId must not be null");
    }
}