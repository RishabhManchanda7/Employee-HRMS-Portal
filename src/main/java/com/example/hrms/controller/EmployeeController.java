package com.example.hrms.controller;

import com.example.hrms.entity.Employee;
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
    public List<Employee> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        Employee e = service.getById(id);
        if (e == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @PostMapping
    public Employee create(@RequestBody Employee e) {
        return service.create(e);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee e) {
        return service.update(id, e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}