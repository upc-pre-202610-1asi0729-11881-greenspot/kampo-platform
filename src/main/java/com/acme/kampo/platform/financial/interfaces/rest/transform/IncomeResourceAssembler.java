package com.acme.kampo.platform.financial.interfaces.rest.transform;

import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.command.RegisterIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateIncomeCommand;
import com.acme.kampo.platform.financial.interfaces.rest.resources.CreateIncomeResource;
import com.acme.kampo.platform.financial.interfaces.rest.resources.IncomeResource;
import com.acme.kampo.platform.financial.interfaces.rest.resources.UpdateIncomeResource;

/**
 * Assembler for Income resource ↔ command/domain translations.
 */
public final class IncomeResourceAssembler {

    private IncomeResourceAssembler() {}

    public static RegisterIncomeCommand toCommand(CreateIncomeResource resource) {
        return new RegisterIncomeCommand(
                resource.description(), resource.amount(), resource.currency(),
                resource.fundoId(), resource.date());
    }

    public static UpdateIncomeCommand toCommand(Long incomeId, UpdateIncomeResource resource) {
        return new UpdateIncomeCommand(incomeId, resource.description(), resource.amount());
    }

    public static IncomeResource toResource(Income income) {
        return new IncomeResource(
                income.getId().getValue(),
                income.getDescription(),
                income.getAmount().amount(),
                income.getAmount().currency(),
                income.getFundoId().getValue(),
                income.getDate());
    }
}
