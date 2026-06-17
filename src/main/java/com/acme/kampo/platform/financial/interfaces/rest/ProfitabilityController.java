package com.acme.kampo.platform.financial.interfaces.rest;


import com.acme.kampo.platform.financial.application.commandservices.ProfitabilityCommandService;
import com.acme.kampo.platform.financial.application.queryservices.ProfitabilityQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.financial.interfaces.rest.resources.*;
import com.acme.kampo.platform.financial.interfaces.rest.transform.ProfitabilityResourceAssembler;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Inbound service in the interface layer for Profitability management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/profitability", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profitability", description = "Endpoints for fundo profitability calculation and history")
public class ProfitabilityController {

    private final ProfitabilityCommandService profitabilityCommandService;
    private final ProfitabilityQueryService   profitabilityQueryService;

    public ProfitabilityController(ProfitabilityCommandService profitabilityCommandService,
                                   ProfitabilityQueryService profitabilityQueryService) {
        this.profitabilityCommandService = profitabilityCommandService;
        this.profitabilityQueryService   = profitabilityQueryService;
    }

    @Operation(summary = "Calculate profitability",
            description = "Calculates netProfit and margin for a fundo and season period, then persists the result")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profitability calculated",
                    content = @Content(schema = @Schema(implementation = ProfitabilityResource.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected calculation error",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateProfitability(
            @Valid @RequestBody CalculateProfitabilityResource resource) {
        log.debug("POST /api/v1/profitability/calculate - fundoId={}, seasonId={}",
                resource.fundoId(), resource.seasonId());
        var result = profitabilityCommandService.handle(
                ProfitabilityResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Profitability, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ProfitabilityResourceAssembler.toResource(s.value()));
            case Result.Failure<Profitability, ApplicationError> f -> {
                var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
            }
        };
    }

    @Operation(summary = "Get latest profitability for a fundo and season")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profitability found",
                    content = @Content(schema = @Schema(implementation = ProfitabilityResource.class))),
            @ApiResponse(responseCode = "404", description = "Not calculated yet",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/fundo/{fundoId}/season/{seasonId}")
    public ResponseEntity<ProfitabilityResource> getProfitabilityByFundoAndSeason(
            @Parameter(description = "Fundo ID", required = true) @PathVariable Long fundoId,
            @Parameter(description = "Season ID", required = true) @PathVariable Long seasonId) {
        log.debug("GET /api/v1/profitability/fundo/{}/season/{}", fundoId, seasonId);
        var result = profitabilityQueryService.handle(
                new GetProfitabilityBySeasonQuery(FundoId.of(fundoId), SeasonId.of(seasonId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProfitabilityResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get profitability history for a fundo",
            description = "Returns all calculations ordered by most recent first")
    @GetMapping("/fundo/{fundoId}/history")
    public ResponseEntity<List<ProfitabilityResource>> getProfitabilityHistory(
            @Parameter(description = "Fundo ID", required = true) @PathVariable Long fundoId) {
        log.debug("GET /api/v1/profitability/fundo/{}/history", fundoId);
        var result = profitabilityQueryService.handle(
                        new GetProfitabilityHistoryQuery(FundoId.of(fundoId)))
                .stream().map(ProfitabilityResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }
}