package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.entity.dynamo.LeaveStatus;
import com.example.hrms.service.LeaveService;
import com.example.hrms.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    private final LeaveService service;
    private final EmployeeService employeeService;

    public LeaveController(LeaveService service, EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LeaveRequestDynamo req) {
        // Validate employee exists
        if (req.getEmployeeCode() == null || employeeService.findByEmployeeCode(req.getEmployeeCode()) == null) {
            return ResponseEntity.badRequest().body("Employee with code '" + req.getEmployeeCode() + "' does not exist");
        }
        return ResponseEntity.ok(service.create(req));
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulk(@RequestBody List<LeaveRequestDynamo> list) {
        // Validate all employees exist
        for (LeaveRequestDynamo req : list) {
            if (req.getEmployeeCode() == null || employeeService.findByEmployeeCode(req.getEmployeeCode()) == null) {
                return ResponseEntity.badRequest().body("Employee with code '" + req.getEmployeeCode() + "' does not exist");
            }
        }
        return ResponseEntity.ok(service.createBulk(list));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
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


