package com.acme.kampo.platform.financial.application.internal.queryservices;


import com.acme.kampo.platform.financial.application.queryservices.IncomeQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.queries.GetAllIncomesQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetIncomeByIdQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetIncomesByFundoQuery;
import com.acme.kampo.platform.financial.domain.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link IncomeQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class IncomeQueryServiceImpl implements IncomeQueryService {

    private final IncomeRepository incomeRepository;

    public IncomeQueryServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Optional<Income> handle(GetIncomeByIdQuery query) {
        return incomeRepository.findById(query.incomeId());
    }

    @Override
    public List<Income> handle(GetIncomesByFundoQuery query) {
        return incomeRepository.findByFundoId(query.fundoId());
    }

    @Override
    public List<Income> handle(GetAllIncomesQuery query) {
        return incomeRepository.findAll();
    }
}

