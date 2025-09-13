package com.example.hrms.service;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDynamo> getAll();
    EmployeeDynamo getById(String id);
    EmployeeDynamo create(EmployeeDynamo employee);
    EmployeeDynamo update(String id, EmployeeDynamo employee);
    void delete(String id);
}
