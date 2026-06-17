package com.acme.kampo.platform.financial.application.internal.queryservices;


import com.acme.kampo.platform.financial.application.queryservices.ProfitabilityQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityByFundoQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityBySeasonQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetProfitabilityHistoryQuery;
import com.acme.kampo.platform.financial.domain.repository.ProfitabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link ProfitabilityQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class ProfitabilityQueryServiceImpl implements ProfitabilityQueryService {

    private final ProfitabilityRepository profitabilityRepository;

    public ProfitabilityQueryServiceImpl(ProfitabilityRepository profitabilityRepository) {
        this.profitabilityRepository = profitabilityRepository;
    }

    @Override
    public Optional<Profitability> handle(GetProfitabilityByFundoQuery query) {
        return profitabilityRepository.findHistoryByFundoId(query.fundoId())
                .stream().findFirst();
    }

    @Override
    public Optional<Profitability> handle(GetProfitabilityBySeasonQuery query) {
        return profitabilityRepository.findByFundoIdAndSeasonId(
                query.fundoId(), query.seasonId());
    }

    @Override
    public List<Profitability> handle(GetProfitabilityHistoryQuery query) {
        return profitabilityRepository.findHistoryByFundoId(query.fundoId());
    }
}
