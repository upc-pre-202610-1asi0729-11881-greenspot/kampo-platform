package com.acme.kampo.platform.financial.interfaces.rest;

import com.acme.kampo.platform.financial.application.commandservices.SaleCommandService;
import com.acme.kampo.platform.financial.application.queryservices.SaleQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.command.CancelSaleCommand;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;
import com.acme.kampo.platform.financial.interfaces.rest.resources.*;
import com.acme.kampo.platform.financial.interfaces.rest.transform.SaleResourceAssembler;
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
 * Inbound service in the interface layer for Sale management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sales", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sales", description = "Endpoints for crop sale management")
public class SaleController {

    private final SaleCommandService saleCommandService;
    private final SaleQueryService   saleQueryService;

    public SaleController(SaleCommandService saleCommandService,
                          SaleQueryService saleQueryService) {
        this.saleCommandService = saleCommandService;
        this.saleQueryService   = saleQueryService;
    }

    @Operation(summary = "Register a crop sale",
            description = "Creates a sale and auto-calculates totalAmount = pricePerUnit × quantity")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSale(@Valid @RequestBody CreateSaleResource resource) {
        log.debug("POST /api/v1/sales - crop={}, fundoId={}", resource.cropName(), resource.fundoId());
        var result = saleCommandService.handle(SaleResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Sale, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(SaleResourceAssembler.toResource(s.value()));
            case Result.Failure<Sale, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Update a sale",
            description = "Updates crop name, quantity and price. Recalculates totalAmount automatically")
    @PutMapping(value = "/{saleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSale(
            @Parameter(description = "Sale ID", required = true) @PathVariable Long saleId,
            @Valid @RequestBody UpdateSaleResource resource) {
        log.debug("PUT /api/v1/sales/{}", saleId);
        var result = saleCommandService.handle(SaleResourceAssembler.toCommand(saleId, resource));
        return switch (result) {
            case Result.Success<Sale, ApplicationError> s ->
                    ResponseEntity.ok(SaleResourceAssembler.toResource(s.value()));
            case Result.Failure<Sale, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Cancel a sale", description = "Marks a sale as cancelled — terminal, cannot be reversed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sale cancelled",
                    content = @Content(schema = @Schema(implementation = SaleResource.class))),
            @ApiResponse(responseCode = "409", description = "Sale already cancelled",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Sale not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{saleId}/cancel")
    public ResponseEntity<?> cancelSale(
            @Parameter(description = "Sale ID", required = true) @PathVariable Long saleId) {
        log.debug("PATCH /api/v1/sales/{}/cancel", saleId);
        var result = saleCommandService.handle(new CancelSaleCommand(saleId));
        return switch (result) {
            case Result.Success<Void, ApplicationError> s -> ResponseEntity.ok().build();
            case Result.Failure<Void, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a sale by ID")
    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResource> getSaleById(
            @Parameter(description = "Sale ID", required = true) @PathVariable Long saleId) {
        log.debug("GET /api/v1/sales/{}", saleId);
        var result = saleQueryService.handle(new GetSaleByIdQuery(SaleId.of(saleId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(SaleResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get sales, optionally filtered by fundo or crop name")
    @GetMapping
    public ResponseEntity<List<SaleResource>> getSales(
            @Parameter(description = "Filter by fundo ID") @RequestParam(required = false) Long fundoId,
            @Parameter(description = "Filter by crop name") @RequestParam(required = false) String cropName) {
        log.debug("GET /api/v1/sales - fundoId={}, cropName={}", fundoId, cropName);
        List<SaleResource> result;
        if (fundoId != null)
            result = saleQueryService.handle(new GetSalesByFundoQuery(FundoId.of(fundoId)))
                    .stream().map(SaleResourceAssembler::toResource).toList();
        else if (cropName != null && !cropName.isBlank())
            result = saleQueryService.handle(new GetSalesByCropQuery(cropName))
                    .stream().map(SaleResourceAssembler::toResource).toList();
        else
            result = List.of();
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        HttpStatus status;
        if (error.code().endsWith("_NOT_FOUND")) {
            status = HttpStatus.NOT_FOUND;
        } else if (error.code().contains("CANCEL") || error.code().endsWith("_CONFLICT")
                || error.code().equals("BUSINESS_RULE_VIOLATION")) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}
