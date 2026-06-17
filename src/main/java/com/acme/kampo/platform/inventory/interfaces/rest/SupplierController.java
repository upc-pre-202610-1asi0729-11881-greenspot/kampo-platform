package com.acme.kampo.platform.inventory.interfaces.rest;

import com.acme.kampo.platform.inventory.application.commandservices.SupplierCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.SupplierQueryService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllSuppliersQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetSupplierByIdQuery;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateSupplierResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.SupplierResource;
import com.acme.kampo.platform.inventory.interfaces.rest.transform.SupplierResourceAssembler;
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
 * Inbound service in the interface layer for Supplier management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Suppliers", description = "Endpoints for supplier management")
public class SupplierController {

    private final SupplierCommandService supplierCommandService;
    private final SupplierQueryService   supplierQueryService;

    public SupplierController(SupplierCommandService supplierCommandService,
                              SupplierQueryService supplierQueryService) {
        this.supplierCommandService = supplierCommandService;
        this.supplierQueryService   = supplierQueryService;
    }

    @Operation(summary = "Register a supplier",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = CreateSupplierResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "409", description = "Conflict — supplier with same email already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSupplier(@Valid @RequestBody CreateSupplierResource resource) {
        log.debug("POST /api/v1/suppliers - name={}", resource.name());
        var result = supplierCommandService.handle(SupplierResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Supplier, ApplicationError> s -> {
                var response = SupplierResourceAssembler.toResource(s.value());
                log.debug("POST /api/v1/suppliers - created id={}", response.id());
                yield ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            case Result.Failure<Supplier, ApplicationError> f -> {
                log.warn("POST /api/v1/suppliers - failed: {}", f.error());
                var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
            }
        };
    }

    @Operation(summary = "Get a supplier by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResource> getSupplierById(
            @Parameter(name = "supplierId", description = "Supplier ID", required = true)
            @PathVariable Long supplierId) {
        log.debug("GET /api/v1/suppliers/{}", supplierId);
        var result = supplierQueryService.handle(new GetSupplierByIdQuery(supplierId));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(SupplierResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suppliers retrieved",
                    content = @Content(schema = @Schema(implementation = SupplierResource[].class)))
    })
    @GetMapping
    public ResponseEntity<List<SupplierResource>> getAllSuppliers() {
        log.debug("GET /api/v1/suppliers");
        var result = supplierQueryService.handle(new GetAllSuppliersQuery()).stream()
                .map(SupplierResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(result);
    }
}
