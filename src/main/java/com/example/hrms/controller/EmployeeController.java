package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeDynamo> all() {
        return service.getAll();
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<EmployeeDynamo> get(@PathVariable String employeeCode) {
        EmployeeDynamo e = service.findByEmployeeCode(employeeCode);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeDynamo e) {
        try {
            EmployeeDynamo created = service.create(e);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{employeeCode}")
    public EmployeeDynamo update(@PathVariable String employeeCode, @RequestBody EmployeeDynamo e) {
        return service.updateByEmployeeCode(employeeCode, e);
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<Void> delete(@PathVariable String employeeCode) {
        service.deleteByEmployeeCode(employeeCode);
        return ResponseEntity.noContent().build();
    }
}