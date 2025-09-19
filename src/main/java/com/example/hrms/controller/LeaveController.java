package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    @PostMapping
    public LeaveRequestDynamo create(@RequestBody LeaveRequestDynamo req) {
        return service.create(req);
    }

    @PostMapping("/bulk")
    public List<LeaveRequestDynamo> createBulk(@RequestBody List<LeaveRequestDynamo> list) {
        return service.createBulk(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDynamo> get(@PathVariable String id) {
        LeaveRequestDynamo r = service.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequestDynamo> byEmployee(@PathVariable String employeeId) {
        return service.getByEmployee(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<LeaveRequestDynamo> byStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestDynamo> approve(@PathVariable String id) {
        LeaveRequestDynamo r = service.approve(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequestDynamo> reject(@PathVariable String id, @RequestBody RejectBody body) {
        LeaveRequestDynamo r = service.reject(id, body != null ? body.getReason() : null);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    public static class RejectBody {
        private String reason;
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}


