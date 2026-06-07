package com.acme.kampo.platform.financial.application.commandservices;

import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.command.CancelSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateSaleCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Sale} write operations.
 */
public interface SaleCommandService {

    Result<Sale, ApplicationError> handle(RegisterSaleCommand command);

    Result<Sale, ApplicationError> handle(UpdateSaleCommand command);

    Result<Void, ApplicationError> handle(CancelSaleCommand command);
}