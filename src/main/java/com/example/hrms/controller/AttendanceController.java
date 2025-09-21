package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.AttendanceDynamo;
import com.example.hrms.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<AttendanceDynamo> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDynamo> get(@PathVariable String id) {
        AttendanceDynamo a = service.getById(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @PostMapping
    public AttendanceDynamo create(@RequestBody AttendanceDynamo a) {
        return service.create(a);
    }

    @PostMapping("/bulk")
    public List<AttendanceDynamo> createBulk(@RequestBody List<AttendanceDynamo> list) {
        return service.createBulk(list);
    }

    @PutMapping("/{id}")
    public AttendanceDynamo update(@PathVariable String id, @RequestBody AttendanceDynamo a) {
        return service.update(id, a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public List<AttendanceDynamo> byEmployee(@PathVariable String employeeId) {
        return service.findByEmployeeId(employeeId);
    }

    @GetMapping("/date/{date}")
    public List<AttendanceDynamo> byDate(@PathVariable String date) {
        return service.findByDate(date);
    }

    @GetMapping("/employee/{employeeId}/date/{date}")
    public List<AttendanceDynamo> byEmployeeAndDate(@PathVariable String employeeId, @PathVariable String date) {
        return service.findByEmployeeIdAndDate(employeeId, date);
    }
}


