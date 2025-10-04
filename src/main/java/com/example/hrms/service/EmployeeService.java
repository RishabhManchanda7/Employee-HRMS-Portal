package com.example.hrms.service;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDynamo> getAll();
    EmployeeDynamo getById(String id);  // Internal use only
    EmployeeDynamo create(EmployeeDynamo employee);
    EmployeeDynamo update(String id, EmployeeDynamo employee);  // Internal use only
    void delete(String id);  // Internal use only

    // Public API methods using employeeCode
    EmployeeDynamo findByEmployeeCode(String empCode);
    EmployeeDynamo updateByEmployeeCode(String employeeCode, EmployeeDynamo employee);
    void deleteByEmployeeCode(String employeeCode);
}
