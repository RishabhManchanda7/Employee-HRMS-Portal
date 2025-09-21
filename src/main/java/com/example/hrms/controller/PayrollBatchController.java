package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.salary.DTO.PayrollJob;
import com.example.hrms.service.impl.PayrollBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll-batch")
@CrossOrigin(origins = "*")
public class PayrollBatchController {

    private final PayrollBatchService batchService;

    public PayrollBatchController(PayrollBatchService batchService) {
        this.batchService = batchService;
    }

    // Add a single job: { "employeeCode": "EMP001", "payPeriod": "2025-09" }
    @PostMapping("/enqueue")
    public ResponseEntity<Integer> enqueue(@RequestBody PayrollJob job) {
        int size = batchService.enqueue(job);
        return ResponseEntity.ok(size);
    }


    @PostMapping("/enqueue-bulk")
    public ResponseEntity<Integer> enqueueBulk(@RequestBody List<PayrollJob> jobs) {
        int size = batchService.enqueueAll(jobs);
        return ResponseEntity.ok(size);
    }


    @GetMapping("/size")
    public ResponseEntity<Integer> size() {
        return ResponseEntity.ok(batchService.size());
    }


    @PostMapping("/process")
    public ResponseEntity<List<PayrollDynamo>> process(@RequestParam(name = "max", required = false) Integer max) {
        int m = (max == null ? 0 : max);
        List<PayrollDynamo> result = batchService.process(m);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear() {
        batchService.clear();
        return ResponseEntity.noContent().build();
    }
}


