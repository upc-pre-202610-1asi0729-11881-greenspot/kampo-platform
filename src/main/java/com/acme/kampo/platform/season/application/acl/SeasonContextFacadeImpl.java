package com.acme.kampo.platform.season.application.acl;

import com.acme.kampo.platform.season.application.commandservices.SeasonCommandService;
import com.acme.kampo.platform.season.application.queryservices.SeasonQueryService;
import com.acme.kampo.platform.season.domain.model.command.CreateSeasonCommand;
import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.season.interfaces.acl.SeasonContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SeasonContextFacadeImpl implements SeasonContextFacade {
    private final SeasonCommandService seasonCommandService;
    private final SeasonQueryService seasonQueryService;

    public SeasonContextFacadeImpl(SeasonCommandService seasonCommandService, SeasonQueryService seasonQueryService){
        this.seasonCommandService = seasonCommandService;
        this.seasonQueryService = seasonQueryService;
    }
    @Override
    public Long createSeason(Long fieldId, String cropName, LocalDate startAt) {
        var command = new CreateSeasonCommand(
                FieldId.of(fieldId),
                cropName,
                startAt);
        return seasonCommandService.handle(command)
                .toOptional()
                .map(season -> season.getId().getValue())
                .orElse(null);
    }
    public Long fetchSeasonById(Long id) {
        var query = new com.acme.kampo.platform.season.domain.model.queries
                .GetSeasonByIdQuery(SeasonId.of(id));
        return seasonQueryService.handle(query)
                .map(season -> season.getId().getValue())
                .orElse(null);
    }
}
