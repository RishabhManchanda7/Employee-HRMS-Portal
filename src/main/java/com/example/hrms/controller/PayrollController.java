package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.service.PayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping
    public List<PayrollDynamo> getAll() {
        return payrollService.getAll();
    }

    @GetMapping("/{payrollCode}")
    public ResponseEntity<PayrollDynamo> get(@PathVariable String payrollCode) {
        PayrollDynamo payroll = payrollService.getByPayrollCode(payrollCode);
        if (payroll == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payroll);
    }

    @PutMapping("/{payrollCode}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String payrollCode, @RequestBody StatusUpdateBody body) {
        try {
            if (body.getStatus() == null) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            PayrollDynamo result = payrollService.updateStatus(payrollCode, body.getStatus());
            if (result == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public static class StatusUpdateBody {
        private String status;
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}