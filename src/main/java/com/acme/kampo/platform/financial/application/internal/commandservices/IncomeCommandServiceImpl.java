package com.acme.kampo.platform.financial.application.internal.commandservices;


import com.acme.kampo.platform.financial.application.commandservices.IncomeCommandService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.command.DeleteIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;
import com.acme.kampo.platform.financial.domain.repository.IncomeRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link IncomeCommandService}.
 */
@Service
@Transactional
public class IncomeCommandServiceImpl implements IncomeCommandService {

    private final IncomeRepository incomeRepository;

    public IncomeCommandServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Result<Income, ApplicationError> handle(RegisterIncomeCommand command) {
        try {
            var income = incomeRepository.save(new Income(command));
            return Result.success(income);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "IncomeCommandService.handle(RegisterIncomeCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Income, ApplicationError> handle(UpdateIncomeCommand command) {
        var incomeOpt = incomeRepository.findById(IncomeId.of(command.incomeId()));
        if (incomeOpt.isEmpty())
            return Result.failure(ApplicationError.notFound("INCOME",
                    String.valueOf(command.incomeId())));
        try {
            var income = incomeOpt.get();
            income.update(command);
            return Result.success(incomeRepository.save(income));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "IncomeCommandService.handle(UpdateIncomeCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteIncomeCommand command) {
        var id = IncomeId.of(command.incomeId());
        if (!incomeRepository.existsById(id))
            return Result.failure(ApplicationError.notFound("INCOME",
                    String.valueOf(command.incomeId())));
        try {
            incomeRepository.delete(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "IncomeCommandService.handle(DeleteIncomeCommand)", e.getMessage()));
        }
    }
}