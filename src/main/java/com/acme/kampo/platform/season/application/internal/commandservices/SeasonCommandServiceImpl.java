package com.acme.kampo.platform.season.application.internal.commandservices;


import com.acme.kampo.platform.season.application.commandservices.SeasonCommandService;
import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.command.AssignCropToSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.CreateSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.EndSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.UpdateSeasonStatusCommand;
import com.acme.kampo.platform.season.domain.repository.SeasonRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SeasonCommandServiceImpl implements SeasonCommandService {

    private final SeasonRepository seasonRepository;

    public SeasonCommandServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    @Transactional
    public Result<Season, ApplicationError> handle(CreateSeasonCommand command) {
        try {
            var activeSeason = seasonRepository.findActiveSeasonByFieldId(command.fieldId());
            if (activeSeason.isPresent()) {
                return Result.failure(ApplicationError.conflict(
                        "SEASON",
                        "There is already an active season for this field"));
            }
            var season = new Season(command);
            var saved = seasonRepository.save(season);
            log.info("Season created: fieldId={}, cropName={}",
                    command.fieldId().getValue(), command.cropName());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Season", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public Result<Season, ApplicationError> handle(AssignCropToSeasonCommand command) {
        try {
            var seasonOpt = seasonRepository.findById(command.seasonId());
            if (seasonOpt.isEmpty()) {
                return Result.failure(ApplicationError.notFound(
                        "Season", command.seasonId().getValue().toString()));
            }
            var season = seasonOpt.get();
            season.assignCrop(command);
            var saved = seasonRepository.save(season);
            log.info("Crop assigned to season: seasonId={}, cropId={}",
                    command.seasonId().getValue(), command.cropId().getValue());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Season", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public Result<Season, ApplicationError> handle(UpdateSeasonStatusCommand command) {
        try {
            var seasonOpt = seasonRepository.findById(command.seasonId());
            if (seasonOpt.isEmpty()) {
                return Result.failure(ApplicationError.notFound(
                        "Season", command.seasonId().getValue().toString()));
            }
            var season = seasonOpt.get();
            season.updateStatus(command);
            var saved = seasonRepository.save(season);
            log.info("Season status updated: seasonId={}, newStatus={}",
                    command.seasonId().getValue(), command.newStatus());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation("SeasonStatus", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public Result<Season, ApplicationError> handle(EndSeasonCommand command) {
        try {
            var seasonOpt = seasonRepository.findById(command.seasonId());
            if (seasonOpt.isEmpty()) {
                return Result.failure(ApplicationError.notFound(
                        "Season", command.seasonId().getValue().toString()));
            }
            var season = seasonOpt.get();
            season.end(command);
            var saved = seasonRepository.save(season);
            log.info("Season ended: seasonId={}, endAt={}",
                    command.seasonId().getValue(), command.endAt());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Season", e.getMessage()));
        }
    }
}
