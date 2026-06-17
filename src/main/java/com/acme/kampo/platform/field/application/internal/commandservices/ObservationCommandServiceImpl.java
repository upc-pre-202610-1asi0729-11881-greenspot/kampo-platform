package com.acme.kampo.platform.field.application.internal.commandservices;

import com.acme.kampo.platform.field.application.commandservices.ObservationCommandService;
import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.commands.RegisterObservationCommand;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.repositories.FieldVisitRepository;
import com.acme.kampo.platform.field.domain.repositories.ObservationRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link ObservationCommandService}.
 *
 * <p>Validates that the referenced {@link com.acme.kampo.platform.fieldoperation.domain.model.aggregates.FieldVisit}
 * exists before persisting the observation.</p>
 */
@Service
@Transactional
public class ObservationCommandServiceImpl implements ObservationCommandService {

    private final ObservationRepository observationRepository;
    private final FieldVisitRepository  fieldVisitRepository;

    public ObservationCommandServiceImpl(ObservationRepository observationRepository,
                                         FieldVisitRepository fieldVisitRepository) {
        this.observationRepository = observationRepository;
        this.fieldVisitRepository  = fieldVisitRepository;
    }

    @Override
    public Result<Observation, ApplicationError> handle(RegisterObservationCommand command) {
        if (!fieldVisitRepository.existsById(FieldVisitId.of(command.fieldVisitId())))
            return Result.failure(ApplicationError.notFound(
                    "FIELD_VISIT", String.valueOf(command.fieldVisitId())));
        try {
            var observation = observationRepository.save(new Observation(command));
            return Result.success(observation);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ObservationCommandService.handle(RegisterObservationCommand)", e.getMessage()));
        }
    }
}