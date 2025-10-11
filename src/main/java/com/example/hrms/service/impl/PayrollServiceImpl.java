package com.example.hrms.service.impl;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.repository.dynamo.PayrollDynamoRepository;
import com.example.hrms.service.PayrollService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollDynamoRepository repository;

    public PayrollServiceImpl(PayrollDynamoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PayrollDynamo> getAll() {
        return repository.findAll();
    }

    @Override
    public PayrollDynamo getByPayrollCode(String payrollCode) {
        return repository.findByPayrollCode(payrollCode);
    }

    @Override
    public PayrollDynamo updateStatus(String payrollCode, String status) {
        PayrollDynamo payroll = repository.findByPayrollCode(payrollCode);
        if (payroll != null) {
            payroll.setStatus(status);
            return repository.save(payroll);
        }
        return null;
    }
}