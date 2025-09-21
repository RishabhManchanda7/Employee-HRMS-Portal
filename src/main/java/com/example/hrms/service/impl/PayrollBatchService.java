package com.example.hrms.service.impl;

import com.example.hrms.salary.DTO.PayrollJob;
import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.salary.DTO.service.SalaryComputationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


@Service
public class PayrollBatchService {

    private final Deque<PayrollJob> queue = new ArrayDeque<>();
    private final SalaryComputationService salaryService;

    public PayrollBatchService(SalaryComputationService salaryService) {
        this.salaryService = salaryService;
    }

    // Add one job to the queue
    public int enqueue(PayrollJob job) {
        if (job == null) return size();
        if (job.getEmployeeCode() == null || job.getPayPeriod() == null) return size();
        queue.addLast(job);
        return size();
    }


    public int enqueueAll(List<PayrollJob> jobs) {
        if (jobs == null) return size();
        for (PayrollJob j : jobs) enqueue(j);
        return size();
    }


    public int size() {
        return queue.size();
    }

    // Process up to 'max' jobs from the front of the queue
    public List<PayrollDynamo> process(int max) {
        List<PayrollDynamo> created = new ArrayList<>();
        if (max <= 0) max = queue.size();
        int count = 0;
        while (!queue.isEmpty() && count < max) {
            PayrollJob job = queue.pollFirst();
            if (job != null) {
                PayrollDynamo p = salaryService.createPayrollRecord(
                        job.getEmployeeCode(),
                        job.getPayPeriod(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                );
                created.add(p);
            }
            count++;
        }
        return created;
    }


    public void clear() {
        queue.clear();
    }
}


