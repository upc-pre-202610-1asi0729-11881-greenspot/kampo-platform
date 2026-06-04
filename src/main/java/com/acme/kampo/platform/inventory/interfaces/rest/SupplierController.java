package com.acme.kampo.platform.inventory.interfaces.rest;

import com.acme.kampo.platform.inventory.application.commandservices.SupplierCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.SupplierQueryService;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllSuppliersQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetSupplierByIdQuery;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateSupplierResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.SupplierResource;
import com.acme.kampo.platform.inventory.interfaces.rest.transform.SupplierResourceAssembler;
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
 * Inbound service in the interface layer for the Supplier bounded context.
 * Orchestrates command and query operations through the application layer:
 * POST operations delegate to {@link SupplierCommandService};
 * GET operations delegate to {@link SupplierQueryService}.
 *
 * @since 1.0
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

    /**
     * Registers a new supplier.
     *
     * @param resource the request payload with name, contact and email
     * @return the created supplier resource with HTTP 201
     * @since 1.0
     */
    @Operation(
            summary = "Register a supplier",
            description = "Creates a new supplier with name, contact reference and email address",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Supplier registration request",
                    content = @Content(schema = @Schema(implementation = CreateSupplierResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SupplierResource> createSupplier(
            @Valid @RequestBody CreateSupplierResource resource) {
        log.debug("POST /api/v1/suppliers - name={}", resource.name());
        var command  = SupplierResourceAssembler.toCommand(resource);
        var supplier = supplierCommandService.handle(command);
        var response = SupplierResourceAssembler.toResource(supplier);
        log.debug("POST /api/v1/suppliers - created id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Gets a supplier by ID.
     *
     * @param supplierId the supplier ID
     * @return the supplier resource if found, or 404 if not
     * @since 1.0
     */
    @Operation(
            summary = "Get a supplier by ID",
            description = "Returns the supplier matching the provided ID")
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
        var query  = new GetSupplierByIdQuery(supplierId);
        var result = supplierQueryService.handle(query);
        if (result.isEmpty()) {
            log.debug("Supplier not found for id={}", supplierId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SupplierResourceAssembler.toResource(result.get()));
    }

    /**
     * Returns all registered suppliers.
     *
     * @return list of all supplier resources
     * @since 1.0
     */
    @Operation(
            summary = "Get all suppliers",
            description = "Returns the complete list of registered suppliers")
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