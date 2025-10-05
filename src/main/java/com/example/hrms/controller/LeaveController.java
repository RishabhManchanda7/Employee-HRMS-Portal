package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.entity.dynamo.LeaveStatus;
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

    @GetMapping("/employee/{employeeCode}")
    public List<LeaveRequestDynamo> byEmployee(@PathVariable String employeeCode) {
        return service.getByEmployee(employeeCode);
    }

    @GetMapping("/status/{status}")
    public List<LeaveRequestDynamo> byStatus(@PathVariable LeaveStatus status) {
        return service.getByStatus(status);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody StatusUpdateBody body) {
        try {
            if (body.getStatus() == null) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            LeaveStatus status = LeaveStatus.valueOf(body.getStatus().toUpperCase());
            LeaveRequestDynamo r = service.updateStatusById(id, status, body.getReason());
            if (r == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(r);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public static class StatusUpdateBody {
        private String status;
        private String reason;
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}


