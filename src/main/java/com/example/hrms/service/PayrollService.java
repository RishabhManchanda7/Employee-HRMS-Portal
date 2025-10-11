package com.example.hrms.service;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import java.util.List;

public interface PayrollService {
    List<PayrollDynamo> getAll();
    PayrollDynamo getByPayrollCode(String payrollCode);
    PayrollDynamo updateStatus(String payrollCode, String status);
}