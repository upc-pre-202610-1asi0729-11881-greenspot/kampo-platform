package com.acme.kampo.platform.financial.application.queryservices;

import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.queries.GetSaleByIdQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetSalesByCropQuery;
import com.acme.kampo.platform.financial.domain.model.queries.GetSalesByFundoQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Sale} read operations.
 */
public interface SaleQueryService {

    Optional<Sale> handle(GetSaleByIdQuery query);

    List<Sale> handle(GetSalesByFundoQuery query);

    List<Sale> handle(GetSalesByCropQuery query);
}
