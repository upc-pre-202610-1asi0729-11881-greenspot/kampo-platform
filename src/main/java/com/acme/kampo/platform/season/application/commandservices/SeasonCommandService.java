package com.acme.kampo.platform.season.application.commandservices;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.command.AssignCropToSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.CreateSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.EndSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.UpdateSeasonStatusCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

public interface SeasonCommandService {

    Result<Season, ApplicationError> handle(CreateSeasonCommand command);
    Result<Season, ApplicationError> handle(AssignCropToSeasonCommand command);
    Result<Season, ApplicationError> handle(UpdateSeasonStatusCommand command);
    Result<Season, ApplicationError> handle(EndSeasonCommand command);
}
