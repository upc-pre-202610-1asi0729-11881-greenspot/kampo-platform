package com.acme.kampo.platform.field.application.internal.commandservices;

import com.acme.kampo.platform.field.application.commandservices.FieldVisitCommandService;
import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.commands.CompleteFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.commands.ScheduleFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.repositories.FieldVisitRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link FieldVisitCommandService}.
 */
@Service
@Transactional
public class FieldVisitCommandServiceImpl implements FieldVisitCommandService {

    private final FieldVisitRepository fieldVisitRepository;

    public FieldVisitCommandServiceImpl(FieldVisitRepository fieldVisitRepository) {
        this.fieldVisitRepository = fieldVisitRepository;
    }

    @Override
    public Result<FieldVisit, ApplicationError> handle(ScheduleFieldVisitCommand command) {
        try {
            var fieldVisit = fieldVisitRepository.save(new FieldVisit(command));
            return Result.success(fieldVisit);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "FieldVisitCommandService.handle(ScheduleFieldVisitCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<FieldVisit, ApplicationError> handle(CompleteFieldVisitCommand command) {
        var visitOpt = fieldVisitRepository.findById(FieldVisitId.of(command.fieldVisitId()));
        if (visitOpt.isEmpty())
            return Result.failure(ApplicationError.notFound(
                    "FIELD_VISIT", String.valueOf(command.fieldVisitId())));
        var visit = visitOpt.get();
        if (visit.getStatus().isCompleted())
            return Result.failure(ApplicationError.businessRuleViolation(
                    "FIELD_VISIT_ALREADY_COMPLETED",
                    "FieldVisit %d is already completed".formatted(command.fieldVisitId())));
        try {
            visit.complete(command);
            return Result.success(fieldVisitRepository.save(visit));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "FieldVisitCommandService.handle(CompleteFieldVisitCommand)", e.getMessage()));
        }
    }
}