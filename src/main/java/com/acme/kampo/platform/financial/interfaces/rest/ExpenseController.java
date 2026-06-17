package com.acme.kampo.platform.financial.interfaces.rest;

import com.acme.kampo.platform.financial.application.commandservices.ExpenseCommandService;
import com.acme.kampo.platform.financial.application.queryservices.ExpenseQueryService;
import com.acme.kampo.platform.financial.domain.model.command.DeleteExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.interfaces.rest.resources.*;
import com.acme.kampo.platform.financial.interfaces.rest.transform.ExpenseResourceAssembler;
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
 * Inbound service in the interface layer for Expense and ExpenseCategory management.
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Expenses", description = "Endpoints for expense and expense category management")
public class ExpenseController {

    private final ExpenseCommandService expenseCommandService;
    private final ExpenseQueryService   expenseQueryService;

    public ExpenseController(ExpenseCommandService expenseCommandService,
                             ExpenseQueryService expenseQueryService) {
        this.expenseCommandService = expenseCommandService;
        this.expenseQueryService   = expenseQueryService;
    }

    // ── Expenses ──────────────────────────────────────────────────────────────

    @Operation(summary = "Register an expense",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = CreateExpenseResource.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Expense registered",
                    content = @Content(schema = @Schema(implementation = ExpenseResource.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/expenses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExpense(@Valid @RequestBody CreateExpenseResource resource) {
        log.debug("POST /api/v1/expenses - fundoId={}", resource.fundoId());
        var result = expenseCommandService.handle(ExpenseResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<?, ?> s -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExpenseResourceAssembler.toResource(
                            (com.acme.kampo.platform.financial.domain.model.aggregates.Expense) s.value()));
            case Result.Failure<?, ?> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Update an expense")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense updated",
                    content = @Content(schema = @Schema(implementation = ExpenseResource.class))),
            @ApiResponse(responseCode = "404", description = "Expense not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping(value = "/api/v1/expenses/{expenseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateExpense(
            @Parameter(description = "Expense ID", required = true) @PathVariable Long expenseId,
            @Valid @RequestBody UpdateExpenseResource resource) {
        log.debug("PUT /api/v1/expenses/{}", expenseId);
        var result = expenseCommandService.handle(
                ExpenseResourceAssembler.toCommand(expenseId, resource));
        return switch (result) {
            case Result.Success<?, ?> s -> ResponseEntity.ok(
                    ExpenseResourceAssembler.toResource(
                            (com.acme.kampo.platform.financial.domain.model.aggregates.Expense) s.value()));
            case Result.Failure<?, ?> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Delete an expense")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Expense deleted"),
            @ApiResponse(responseCode = "404", description = "Expense not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/api/v1/expenses/{expenseId}")
    public ResponseEntity<?> deleteExpense(
            @Parameter(description = "Expense ID", required = true) @PathVariable Long expenseId) {
        log.debug("DELETE /api/v1/expenses/{}", expenseId);
        var result = expenseCommandService.handle(new DeleteExpenseCommand(expenseId));
        return switch (result) {
            case Result.Success<Void, ApplicationError> s -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an expense by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expense found",
                    content = @Content(schema = @Schema(implementation = ExpenseResource.class))),
            @ApiResponse(responseCode = "404", description = "Expense not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/api/v1/expenses/{expenseId}")
    public ResponseEntity<ExpenseResource> getExpenseById(
            @Parameter(description = "Expense ID", required = true) @PathVariable Long expenseId) {
        log.debug("GET /api/v1/expenses/{}", expenseId);
        var result = expenseQueryService.handle(new GetExpenseByIdQuery(ExpenseId.of(expenseId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ExpenseResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get expenses by fundo")
    @GetMapping("/api/v1/expenses")
    public ResponseEntity<List<ExpenseResource>> getExpenses(
            @Parameter(description = "Filter by fundo ID") @RequestParam(required = false) Long fundoId,
            @Parameter(description = "Filter by type") @RequestParam(required = false) ExpenseType type) {
        log.debug("GET /api/v1/expenses - fundoId={}, type={}", fundoId, type);
        List<ExpenseResource> result;
        if (fundoId != null)
            result = expenseQueryService.handle(new GetExpensesByFundoQuery(FundoId.of(fundoId)))
                    .stream().map(ExpenseResourceAssembler::toResource).toList();
        else if (type != null)
            result = expenseQueryService.handle(new GetExpensesByTypeQuery(type))
                    .stream().map(ExpenseResourceAssembler::toResource).toList();
        else
            result = expenseQueryService.handle(new GetAllExpensesQuery())
                    .stream().map(ExpenseResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Categories ────────────────────────────────────────────────────────────

    @Operation(summary = "Create an expense category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created",
                    content = @Content(schema = @Schema(implementation = ExpenseCategoryResource.class))),
            @ApiResponse(responseCode = "409", description = "Category name already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/expense-categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateExpenseCategoryResource resource) {
        log.debug("POST /api/v1/expense-categories - name={}", resource.name());
        var result = expenseCommandService.handle(ExpenseResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<?, ?> s -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExpenseResourceAssembler.toResource(
                            (com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory) s.value()));
            case Result.Failure<?, ?> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get all expense categories")
    @GetMapping("/api/v1/expense-categories")
    public ResponseEntity<List<ExpenseCategoryResource>> getAllCategories() {
        log.debug("GET /api/v1/expense-categories");
        var result = expenseQueryService.handle(new GetExpenseCategoriesQuery())
                .stream().map(ExpenseResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Error helper ──────────────────────────────────────────────────────────

    private ResponseEntity<ProblemDetail> toErrorResponse(Object error) {
        var err = (ApplicationError) error;
        var status = err.code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND
                : err.code().endsWith("_CONFLICT")    ? HttpStatus.CONFLICT
                  : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(err.code());
        problem.setDetail(err.message());
        return ResponseEntity.status(status).body(problem);
    }
}