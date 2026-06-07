package com.acme.kampo.platform.financial.application.queryservices;

import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.queries.GetAllIncomesQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetIncomeByIdQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetIncomesByFundoQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Income} read operations.
 */
public interface IncomeQueryService {

    Optional<Income> handle(GetIncomeByIdQuery query);

    List<Income> handle(GetIncomesByFundoQuery query);

    List<Income> handle(GetAllIncomesQuery query);
}