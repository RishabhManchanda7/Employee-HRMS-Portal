package com.example.hrms.service.impl;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.repository.dynamo.EmployeeDynamoRepository;
import com.example.hrms.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDynamoService implements EmployeeService {

    private final EmployeeDynamoRepository employeeRepo;

    public EmployeeDynamoService(EmployeeDynamoRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<EmployeeDynamo> getAll() {
        return (List<EmployeeDynamo>) employeeRepo.findAll();
    }

    @Override
    public EmployeeDynamo getById(String id) {
        return employeeRepo.findById(id).orElse(null);
    }

    @Override
    public EmployeeDynamo create(EmployeeDynamo employee) {
        employee.setTimestamps();
        return employeeRepo.save(employee);
    }

    @Override
    public EmployeeDynamo update(String id, EmployeeDynamo employee) {
        Optional<EmployeeDynamo> existing = employeeRepo.findById(id);
        if (existing.isPresent()) {
            employee.setId(id);
            employee.setTimestamps();
            return employeeRepo.save(employee);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public EmployeeDynamo findByEmployeeCode(String empCode) {
        return employeeRepo.findByEmployeeCode(empCode).orElse(null);
    }

}
