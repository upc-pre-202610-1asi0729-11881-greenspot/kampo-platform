package com.acme.kampo.platform.inventory.interfaces.rest;

import com.acme.kampo.platform.inventory.application.commandservices.OrderInputCommandService;
import com.acme.kampo.platform.inventory.application.queryservices.OrderInputQueryService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllOrderInputsQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetOrderInputByIdQuery;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.CreateOrderInputResource;
import com.acme.kampo.platform.inventory.interfaces.rest.resources.OrderInputResource;
import com.acme.kampo.platform.inventory.interfaces.rest.transform.OrderInputResourceAssembler;
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

import java.time.LocalDateTime;
import java.util.List;

/**
 * Inbound service in the interface layer for OrderInput management.
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

    @Operation(summary = "Place an input order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = CreateOrderInputResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order placed successfully",
                    content = @Content(schema = @Schema(implementation = OrderInputResource.class))),
            @ApiResponse(responseCode = "404", description = "Inventory item not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrderInput(@Valid @RequestBody CreateOrderInputResource resource) {
        log.debug("POST /api/v1/order-inputs - inventoryId={}, supplierId={}, quantity={}",
                resource.inventoryId(), resource.supplierId(), resource.quantity());
        var result = orderInputCommandService.handle(OrderInputResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<OrderInput, ApplicationError> s -> {
                var response = OrderInputResourceAssembler.toResource(s.value());
                log.debug("POST /api/v1/order-inputs - created id={}", response.id());
                yield ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            case Result.Failure<OrderInput, ApplicationError> f -> {
                log.warn("POST /api/v1/order-inputs - failed: {}", f.error());
                var status  = f.error().code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                var problem = ProblemDetail.forStatus(status);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(status).body(problem);
            }
        };
    }

    @Operation(summary = "Receive an input order",
            description = "Marks a PENDING order as RECEIVED and adds the ordered quantity to inventory stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order received successfully",
                    content = @Content(schema = @Schema(implementation = OrderInputResource.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Order already in terminal state",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{orderId}/receive")
    public ResponseEntity<?> receiveOrderInput(
            @Parameter(name = "orderId", description = "ID of the order to receive", required = true)
            @PathVariable Long orderId) {
        log.debug("PATCH /api/v1/order-inputs/{}/receive", orderId);
        var result = orderInputCommandService.handle(new ReceiveInputCommand(orderId, LocalDateTime.now()));
        return switch (result) {
            case Result.Success<OrderInput, ApplicationError> s -> {
                var response = OrderInputResourceAssembler.toResource(s.value());
                log.debug("PATCH /api/v1/order-inputs/{}/receive - status={}", orderId, response.status());
                yield ResponseEntity.ok(response);
            }
            case Result.Failure<OrderInput, ApplicationError> f -> {
                log.warn("PATCH /api/v1/order-inputs/{}/receive - failed: {}", orderId, f.error());
                var status = f.error().code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT;
                var problem = ProblemDetail.forStatus(status);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(status).body(problem);
            }
        };
    }

    @Operation(summary = "Get an input order by ID")
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
        var result = orderInputQueryService.handle(new GetOrderInputByIdQuery(orderId));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OrderInputResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all input orders")
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
