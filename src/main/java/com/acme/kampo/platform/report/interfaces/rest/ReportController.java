package com.acme.kampo.platform.report.interfaces.rest;

import com.acme.kampo.platform.report.application.commandservices.ReportCommandService;
import com.acme.kampo.platform.report.application.queryservices.ReportQueryService;
import com.acme.kampo.platform.report.domain.model.commands.GenerateRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.commands.GenerateReportCommand;
import com.acme.kampo.platform.report.domain.model.commands.ImplementRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.queries.GetAllReportsQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetRecommendationsByReportIdQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetReportByIdQuery;
import com.acme.kampo.platform.report.domain.model.valueObjects.FileUrl;
import com.acme.kampo.platform.report.domain.model.valueObjects.RecommendationId;
import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;
import com.acme.kampo.platform.report.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.report.domain.model.valueObjects.UserId;
import com.acme.kampo.platform.report.interfaces.rest.resources.CreateRecommendationResource;
import com.acme.kampo.platform.report.interfaces.rest.resources.CreateReportResource;
import com.acme.kampo.platform.report.interfaces.rest.resources.RecommendationResource;
import com.acme.kampo.platform.report.interfaces.rest.resources.ReportResource;
import com.acme.kampo.platform.report.interfaces.rest.transform.RecommendationResourceAssembler;
import com.acme.kampo.platform.report.interfaces.rest.transform.ReportResourceAssembler;
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
@RequestMapping(value = "/api/v1/reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Reports", description = "Endpoints for report management")
public class ReportController {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportController(ReportCommandService reportCommandService,
                            ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    @Operation(summary = "Generate a report")
    @PostMapping
    public ResponseEntity<?> generateReport(@Valid @RequestBody CreateReportResource resource) {
        var command = new GenerateReportCommand(
                resource.type(),
                resource.fileUrl() != null ? FileUrl.of(resource.fileUrl()) : null,
                UserId.of(resource.userId()),
                SeasonId.of(resource.seasonId()));
        var result = reportCommandService.handle(command);
        return result.toOptional()
                .map(report -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ReportResourceAssembler.toResource(report)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get report by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReportResource> getReportById(@PathVariable Long id) {
        var query = new GetReportByIdQuery(ReportId.of(id));
        return reportQueryService.handle(query)
                .map(report -> ResponseEntity.ok(ReportResourceAssembler.toResource(report)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all reports")
    @GetMapping
    public ResponseEntity<List<ReportResource>> getAllReports() {
        var reports = reportQueryService.handle(new GetAllReportsQuery())
                .stream()
                .map(ReportResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(reports);
    }

    @Operation(summary = "Generate a recommendation for a report")
    @PostMapping("/{id}/recommendations")
    public ResponseEntity<?> generateRecommendation(
            @PathVariable Long id,
            @Valid @RequestBody CreateRecommendationResource resource) {
        var command = new GenerateRecommendationCommand(
                resource.priority(),
                ReportId.of(id));
        var result = reportCommandService.handle(command);
        return result.toOptional()
                .map(recommendation -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(RecommendationResourceAssembler.toResource(recommendation)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get recommendations by report ID")
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<RecommendationResource>> getRecommendationsByReportId(
            @PathVariable Long id) {
        var query = new GetRecommendationsByReportIdQuery(ReportId.of(id));
        var recommendations = reportQueryService.handle(query)
                .stream()
                .map(RecommendationResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "Implement a recommendation")
    @PatchMapping("/recommendations/{recommendationId}/implement")
    public ResponseEntity<?> implementRecommendation(
            @PathVariable Long recommendationId) {
        var command = new ImplementRecommendationCommand(
                RecommendationId.of(recommendationId));
        var result = reportCommandService.handle(command);
        return result.toOptional()
                .map(recommendation -> ResponseEntity
                        .ok(RecommendationResourceAssembler.toResource(recommendation)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}