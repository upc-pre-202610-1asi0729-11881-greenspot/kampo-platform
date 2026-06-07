package com.acme.kampo.platform.financial.interfaces.rest;

import com.acme.kampo.platform.financial.application.commandservices.IncomeCommandService;
import com.acme.kampo.platform.financial.application.queryservices.IncomeQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.command.DeleteIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;
import com.acme.kampo.platform.financial.interfaces.rest.resources.*;
import com.acme.kampo.platform.financial.interfaces.rest.transform.IncomeResourceAssembler;
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
 * Inbound service in the interface layer for Income management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/incomes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Incomes", description = "Endpoints for income management")
public class IncomeController {

    private final IncomeCommandService incomeCommandService;
    private final IncomeQueryService   incomeQueryService;

    public IncomeController(IncomeCommandService incomeCommandService,
                            IncomeQueryService incomeQueryService) {
        this.incomeCommandService = incomeCommandService;
        this.incomeQueryService   = incomeQueryService;
    }

    @Operation(summary = "Register an income entry")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Income registered",
                    content = @Content(schema = @Schema(implementation = IncomeResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createIncome(@Valid @RequestBody CreateIncomeResource resource) {
        log.debug("POST /api/v1/incomes - fundoId={}", resource.fundoId());
        var result = incomeCommandService.handle(IncomeResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Income, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(IncomeResourceAssembler.toResource(s.value()));
            case Result.Failure<Income, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Update an income entry")
    @PutMapping(value = "/{incomeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateIncome(
            @Parameter(description = "Income ID", required = true) @PathVariable Long incomeId,
            @Valid @RequestBody UpdateIncomeResource resource) {
        log.debug("PUT /api/v1/incomes/{}", incomeId);
        var result = incomeCommandService.handle(
                IncomeResourceAssembler.toCommand(incomeId, resource));
        return switch (result) {
            case Result.Success<Income, ApplicationError> s ->
                    ResponseEntity.ok(IncomeResourceAssembler.toResource(s.value()));
            case Result.Failure<Income, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Delete an income entry")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Income deleted"),
            @ApiResponse(responseCode = "404", description = "Income not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{incomeId}")
    public ResponseEntity<?> deleteIncome(
            @Parameter(description = "Income ID", required = true) @PathVariable Long incomeId) {
        log.debug("DELETE /api/v1/incomes/{}", incomeId);
        var result = incomeCommandService.handle(new DeleteIncomeCommand(incomeId));
        return switch (result) {
            case Result.Success<Void, ApplicationError> s -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an income entry by ID")
    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeResource> getIncomeById(
            @Parameter(description = "Income ID", required = true) @PathVariable Long incomeId) {
        log.debug("GET /api/v1/incomes/{}", incomeId);
        var result = incomeQueryService.handle(new GetIncomeByIdQuery(IncomeId.of(incomeId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(IncomeResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get income entries, optionally filtered by fundo")
    @GetMapping
    public ResponseEntity<List<IncomeResource>> getIncomes(
            @Parameter(description = "Filter by fundo ID") @RequestParam(required = false) Long fundoId) {
        log.debug("GET /api/v1/incomes - fundoId={}", fundoId);
        var result = (fundoId != null
                ? incomeQueryService.handle(new GetIncomesByFundoQuery(FundoId.of(fundoId)))
                : incomeQueryService.handle(new GetAllIncomesQuery()))
                .stream().map(IncomeResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        var status = error.code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}
