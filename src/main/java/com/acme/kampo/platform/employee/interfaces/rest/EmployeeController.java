package com.acme.kampo.platform.employee.interfaces.rest;

import com.acme.kampo.platform.employee.application.commandservices.EmployeeCommandService;
import com.acme.kampo.platform.employee.application.queryservices.EmployeeQueryService;
import com.acme.kampo.platform.employee.domain.model.commands.DeleteEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.queries.GetEmployeeByIdQuery;
import com.acme.kampo.platform.employee.interfaces.rest.resources.CreateEmployeeResource;
import com.acme.kampo.platform.employee.interfaces.rest.resources.EmployeeResource;
import com.acme.kampo.platform.employee.interfaces.rest.resources.ModifyEmployeeResource;
import com.acme.kampo.platform.employee.interfaces.rest.transform.CreateEmployeeAssembler;
import com.acme.kampo.platform.employee.interfaces.rest.transform.EmployeeAssembler;
import com.acme.kampo.platform.employee.interfaces.rest.transform.ModifyEmployeeAssembler;
import com.acme.kampo.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Employee Management Endpoints")
public class EmployeeController {

    private final EmployeeCommandService employeeCommandService;
    private final EmployeeQueryService employeeQueryService;

    public EmployeeController(EmployeeCommandService employeeCommandService,
                              EmployeeQueryService employeeQueryService) {
        this.employeeCommandService = employeeCommandService;
        this.employeeQueryService = employeeQueryService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeResource resource) {
        var result = employeeCommandService.handle(CreateEmployeeAssembler.toCommand(resource));
        if (result instanceof Result.Failure<?, ?>) return ResponseEntity.badRequest().build();
        var employee = ((Result.Success<?, ?>) result).value();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmployeeAssembler.toResource((com.acme.kampo.platform.employee.domain.model.aggregates.Employee) employee));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> modifyEmployee(@PathVariable Long employeeId,
                                            @RequestBody ModifyEmployeeResource resource) {
        var result = employeeCommandService.handle(ModifyEmployeeAssembler.toCommand(employeeId, resource));
        if (result instanceof Result.Failure<?, ?>) return ResponseEntity.badRequest().build();
        var employee = ((Result.Success<?, ?>) result).value();
        return ResponseEntity.ok(EmployeeAssembler.toResource((com.acme.kampo.platform.employee.domain.model.aggregates.Employee) employee));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        var result = employeeCommandService.handle(new DeleteEmployeeCommand(employeeId));
        if (result instanceof Result.Failure<?, ?>) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResource> getEmployeeById(@PathVariable Long employeeId) {
        var result = employeeQueryService.handle(new GetEmployeeByIdQuery(employeeId));
        return result.map(employee -> ResponseEntity.ok(EmployeeAssembler.toResource(employee)))
                .orElse(ResponseEntity.notFound().build());
    }
}