package com.acme.kampo.platform.financial.application.commandservices;

import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.command.DeleteIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateIncomeCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Income} write operations.
 */
public interface IncomeCommandService {

    Result<Income, ApplicationError> handle(RegisterIncomeCommand command);

    Result<Income, ApplicationError> handle(UpdateIncomeCommand command);

    Result<Void, ApplicationError> handle(DeleteIncomeCommand command);
}

