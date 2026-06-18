package com.acme.kampo.platform.season.interfaces.rest;

import com.acme.kampo.platform.season.application.commandservices.SeasonCommandService;
import com.acme.kampo.platform.season.application.queryservices.SeasonQueryService;
import com.acme.kampo.platform.season.domain.model.command.AssignCropToSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.CreateSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.EndSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.UpdateSeasonStatusCommand;
import com.acme.kampo.platform.season.domain.model.queries.GetActiveSeasonByFieldIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonByIdQuery;
import com.acme.kampo.platform.season.domain.model.queries.GetSeasonsByFieldIdQuery;
import com.acme.kampo.platform.season.domain.model.valueobjects.CropId;
import com.acme.kampo.platform.season.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;
import com.acme.kampo.platform.season.interfaces.rest.resources.AssignCropResource;
import com.acme.kampo.platform.season.interfaces.rest.resources.CreateSeasonResource;
import com.acme.kampo.platform.season.interfaces.rest.resources.EndSeasonResource;
import com.acme.kampo.platform.season.interfaces.rest.resources.SeasonResource;
import com.acme.kampo.platform.season.interfaces.rest.resources.UpdateSeasonStatusResource;
import com.acme.kampo.platform.season.interfaces.rest.transform.SeasonResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/seasons", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Seasons", description = "Endpoints for season management")
public class SeasonController {

    private final SeasonCommandService seasonCommandService;
    private final SeasonQueryService seasonQueryService;

    public SeasonController(SeasonCommandService seasonCommandService,
                            SeasonQueryService seasonQueryService) {
        this.seasonCommandService = seasonCommandService;
        this.seasonQueryService = seasonQueryService;
    }

    @Operation(summary = "Create a season")
    @PostMapping
    public ResponseEntity<?> createSeason(@Valid @RequestBody CreateSeasonResource resource) {
        var command = new CreateSeasonCommand(
                FieldId.of(resource.fieldId()),
                resource.cropName(),
                resource.startAt());
        var result = seasonCommandService.handle(command);
        return result.toOptional()
                .map(season -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(SeasonResourceAssembler.toResource(season)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get season by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SeasonResource> getSeasonById(@PathVariable Long id) {
        var query = new GetSeasonByIdQuery(SeasonId.of(id));
        return seasonQueryService.handle(query)
                .map(season -> ResponseEntity.ok(SeasonResourceAssembler.toResource(season)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all seasons by field ID")
    @GetMapping
    public ResponseEntity<List<SeasonResource>> getSeasonsByFieldId(
            @RequestParam Long fieldId) {
        var query = new GetSeasonsByFieldIdQuery(FieldId.of(fieldId));
        var seasons = seasonQueryService.handle(query)
                .stream()
                .map(SeasonResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(seasons);
    }

    @Operation(summary = "Get active season by field ID")
    @GetMapping("/active")
    public ResponseEntity<SeasonResource> getActiveSeasonByFieldId(
            @RequestParam Long fieldId) {
        var query = new GetActiveSeasonByFieldIdQuery(FieldId.of(fieldId));
        return seasonQueryService.handle(query)
                .map(season -> ResponseEntity.ok(SeasonResourceAssembler.toResource(season)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Assign crop to season")
    @PatchMapping("/{id}/crop")
    public ResponseEntity<?> assignCrop(
            @PathVariable Long id,
            @Valid @RequestBody AssignCropResource resource) {
        var command = new AssignCropToSeasonCommand(
                SeasonId.of(id),
                CropId.of(resource.cropId()));
        var result = seasonCommandService.handle(command);
        return result.toOptional()
                .map(season -> ResponseEntity.ok(SeasonResourceAssembler.toResource(season)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Update season status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSeasonStatusResource resource) {
        var command = new UpdateSeasonStatusCommand(
                SeasonId.of(id),
                resource.status());
        var result = seasonCommandService.handle(command);
        return result.toOptional()
                .map(season -> ResponseEntity.ok(SeasonResourceAssembler.toResource(season)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "End a season")
    @PatchMapping("/{id}/end")
    public ResponseEntity<?> endSeason(
            @PathVariable Long id,
            @Valid @RequestBody EndSeasonResource resource) {
        var command = new EndSeasonCommand(
                SeasonId.of(id),
                resource.endAt());
        var result = seasonCommandService.handle(command);
        return result.toOptional()
                .map(season -> ResponseEntity.ok(SeasonResourceAssembler.toResource(season)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}