package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.DepartmentDynamo;
import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentDynamoRepository repository;

    public DepartmentController(DepartmentDynamoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DepartmentDynamo> all() {
        return (List<DepartmentDynamo>) repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDynamo> get(@PathVariable String id) {
        DepartmentDynamo dept = repository.findById(id).orElse(null);
        if (dept == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dept);
    }

    @PostMapping
    public DepartmentDynamo create(@RequestBody DepartmentDynamo dept) {
        dept.setTimestamps();
        return repository.save(dept);
    }

    @PutMapping("/{id}")
    public DepartmentDynamo update(@PathVariable String id, @RequestBody DepartmentDynamo dept) {
        dept.setId(id);
        dept.setTimestamps();
        return repository.save(dept);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}