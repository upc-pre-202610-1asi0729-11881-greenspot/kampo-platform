package com.acme.kampo.platform.inventory.interfaces.rest;

import com.acme.kampo.platform.inventory.application.commandservices.OrderInputCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.OrderInputQueryService;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllOrderInputsQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetOrderInputByIdQuery;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateOrderInputResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.OrderInputResource;
import com.acme.kampo.platform.inventory.interfaces.rest.transform.OrderInputResourceAssembler;
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

import java.time.LocalDateTime;
import java.util.List;

/**
 * Inbound service in the interface layer for the OrderInput bounded context.
 * Orchestrates command and query operations through the application layer:
 * POST/PATCH operations delegate to {@link OrderInputCommandService};
 * GET operations delegate to {@link OrderInputQueryService}.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/order-inputs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order Inputs", description = "Endpoints for input order management")
public class OrderInputController {

    private final OrderInputCommandService orderInputCommandService;
    private final OrderInputQueryService   orderInputQueryService;

    public OrderInputController(OrderInputCommandService orderInputCommandService,
                                OrderInputQueryService orderInputQueryService) {
        this.orderInputCommandService = orderInputCommandService;
        this.orderInputQueryService   = orderInputQueryService;
    }

    /**
     * Places a new input order.
     *
     * @param resource the request payload with inventoryId, supplierId and quantity
     * @return the created order resource with HTTP 201
     * @since 1.0
     */
    @Operation(
            summary = "Place an input order",
            description = "Creates a new input order for an inventory item from a given supplier",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Order creation request",
                    content = @Content(schema = @Schema(implementation = CreateOrderInputResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order placed successfully",
                    content = @Content(schema = @Schema(implementation = OrderInputResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload or inventory not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderInputResource> createOrderInput(
            @Valid @RequestBody CreateOrderInputResource resource) {
        log.debug("POST /api/v1/order-inputs - inventoryId={}, supplierId={}, quantity={}",
                resource.inventoryId(), resource.supplierId(), resource.quantity());
        var command  = OrderInputResourceAssembler.toCommand(resource);
        var order    = orderInputCommandService.handle(command);
        var response = OrderInputResourceAssembler.toResource(order);
        log.debug("POST /api/v1/order-inputs - created id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Marks a pending order as received, automatically updating the inventory stock.
     *
     * @param orderId the ID of the order to receive
     * @return the updated order resource in RECEIVED status
     * @since 1.0
     */
    @Operation(
            summary = "Receive an input order",
            description = "Marks a PENDING order as RECEIVED and adds the ordered quantity to inventory stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order received successfully",
                    content = @Content(schema = @Schema(implementation = OrderInputResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — order already in terminal state",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{orderId}/receive")
    public ResponseEntity<OrderInputResource> receiveOrderInput(
            @Parameter(name = "orderId", description = "ID of the order to receive", required = true)
            @PathVariable Long orderId) {
        log.debug("PATCH /api/v1/order-inputs/{}/receive", orderId);
        var command  = new ReceiveInputCommand(orderId, LocalDateTime.now());
        var order    = orderInputCommandService.handle(command);
        var response = OrderInputResourceAssembler.toResource(order);
        log.debug("PATCH /api/v1/order-inputs/{}/receive - status={}", orderId, response.status());
        return ResponseEntity.ok(response);
    }

    /**
     * Gets an input order by ID.
     *
     * @param orderId the order ID
     * @return the order resource if found, or 404 if not
     * @since 1.0
     */
    @Operation(
            summary = "Get an input order by ID",
            description = "Returns the input order matching the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = OrderInputResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInputResource> getOrderInputById(
            @Parameter(name = "orderId", description = "Order ID", required = true)
            @PathVariable Long orderId) {
        log.debug("GET /api/v1/order-inputs/{}", orderId);
        var query  = new GetOrderInputByIdQuery(orderId);
        var result = orderInputQueryService.handle(query);
        if (result.isEmpty()) {
            log.debug("Order not found for id={}", orderId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(OrderInputResourceAssembler.toResource(result.get()));
    }

    /**
     * Returns all input orders.
     *
     * @return list of all order resources
     * @since 1.0
     */
    @Operation(
            summary = "Get all input orders",
            description = "Returns the complete list of input orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved",
                    content = @Content(schema = @Schema(implementation = OrderInputResource[].class)))
    })
    @GetMapping
    public ResponseEntity<List<OrderInputResource>> getAllOrderInputs() {
        log.debug("GET /api/v1/order-inputs");
        var result = orderInputQueryService.handle(new GetAllOrderInputsQuery()).stream()
                .map(OrderInputResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(result);
    }
}
