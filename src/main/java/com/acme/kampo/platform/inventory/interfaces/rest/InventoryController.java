package com.acme.kampo.platform.inventory.interfaces.rest;

import com.acme.kampo.platform.inventory.application.commandservices.InventoryCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.InventoryQueryService;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllInventoryQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetInventoryByIdQuery;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateInventoryResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.InventoryResource;
import com.acme.kampo.platform.inventory.interfaces.rest.transform.InventoryResourceAssembler;
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
 * Inbound service in the interface layer for the Inventory bounded context.
 * Orchestrates command and query operations through the application layer:
 * POST operations delegate to {@link InventoryCommandService};
 * GET operations delegate to {@link InventoryQueryService}.
 * Translates between HTTP resources and domain models using assemblers.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory", description = "Endpoints for inventory management")
public class InventoryController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService   inventoryQueryService;

    public InventoryController(InventoryCommandService inventoryCommandService,
                               InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService   = inventoryQueryService;
    }

    /**
     * Creates a new inventory item.
     *
     * @param resource the request payload with name, quantity, unit and minStock
     * @return the created inventory resource with HTTP 201
     * @since 1.0
     */
    @Operation(
            summary = "Create an inventory item",
            description = "Registers a new inventory item with its initial stock and minimum threshold",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Inventory creation request",
                    content = @Content(schema = @Schema(implementation = CreateInventoryResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory item created",
                    content = @Content(schema = @Schema(implementation = InventoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryResource> createInventory(
            @Valid @RequestBody CreateInventoryResource resource) {
        log.debug("POST /api/v1/inventory - name={}", resource.name());
        var command   = InventoryResourceAssembler.toCommand(resource);
        var inventory = inventoryCommandService.handle(command);
        var response  = InventoryResourceAssembler.toResource(inventory);
        log.debug("POST /api/v1/inventory - created id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Gets an inventory item by ID.
     *
     * @param inventoryId the inventory item ID
     * @return the inventory resource if found, or 404 if not
     * @since 1.0
     */
    @Operation(
            summary = "Get an inventory item by ID",
            description = "Returns the inventory item matching the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory item found",
                    content = @Content(schema = @Schema(implementation = InventoryResource.class))),
            @ApiResponse(responseCode = "404", description = "Inventory item not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResource> getInventoryById(
            @Parameter(name = "inventoryId", description = "Inventory item ID", required = true)
            @PathVariable Long inventoryId) {
        log.debug("GET /api/v1/inventory/{}", inventoryId);
        var query  = new GetInventoryByIdQuery(inventoryId);
        var result = inventoryQueryService.handle(query);
        if (result.isEmpty()) {
            log.debug("Inventory item not found for id={}", inventoryId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(InventoryResourceAssembler.toResource(result.get()));
    }

    /**
     * Returns all inventory items.
     *
     * @return list of all inventory resources
     * @since 1.0
     */
    @Operation(
            summary = "Get all inventory items",
            description = "Returns the complete list of registered inventory items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory items retrieved",
                    content = @Content(schema = @Schema(implementation = InventoryResource[].class)))
    })
    @GetMapping
    public ResponseEntity<List<InventoryResource>> getAllInventory() {
        log.debug("GET /api/v1/inventory");
        var result = inventoryQueryService.handle(new GetAllInventoryQuery()).stream()
                .map(InventoryResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(result);
    }
}