package com.acme.kampo.platform.financial.application.internal.queryservices;


import com.acme.kampo.platform.financial.application.queryservices.SaleQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.queries.GetSaleByIdQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetSalesByCropQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetSalesByFundoQuery;
import com.acme.kampo.platform.financial.domain.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link SaleQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class SaleQueryServiceImpl implements SaleQueryService {

    private final SaleRepository saleRepository;

    public SaleQueryServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdQuery query) {
        return saleRepository.findById(query.saleId());
    }

    @Override
    public List<Sale> handle(GetSalesByFundoQuery query) {
        return saleRepository.findByFundoId(query.fundoId());
    }

    @Override
    public List<Sale> handle(GetSalesByCropQuery query) {
        return saleRepository.findByCropName(query.cropName());
    }
}
