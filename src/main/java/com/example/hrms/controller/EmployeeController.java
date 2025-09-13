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

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDynamo> get(@PathVariable String id) {
        EmployeeDynamo e = service.getById(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public EmployeeDynamo create(@RequestBody EmployeeDynamo e) {
        return service.create(e);
    }

    @PutMapping("/{id}")
    public EmployeeDynamo update(@PathVariable String id, @RequestBody EmployeeDynamo e) {
        return service.update(id, e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}