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
        // Auto-generate employee code if not provided
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().isEmpty()) {
            employee.setEmployeeCode(generateEmployeeCode());
        }
        
        Optional<EmployeeDynamo> employeeDynamo = employeeRepo.findByEmployeeCode(employee.getEmployeeCode());
        if(employeeDynamo.isPresent()){
            throw new RuntimeException("Employee already exists");
        }
        
        // Check email uniqueness
        List<EmployeeDynamo> existingByEmail = employeeRepo.findByEmail(employee.getEmail());
        if(!existingByEmail.isEmpty()){
            throw new RuntimeException("Email already exists");
        }
        
        // Set ID and timestamps
        employee.setId(employee.generateHashId(employee.getEmployeeCode()));
        
        // Set timestamps directly
        String now = java.time.Instant.now().toString();
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        
        return employeeRepo.save(employee);
    }
    
    private String generateEmployeeCode() {
        List<EmployeeDynamo> allEmployees = (List<EmployeeDynamo>) employeeRepo.findAll();
        int maxNumber = 0;
        
        for (EmployeeDynamo emp : allEmployees) {
            String code = emp.getEmployeeCode();
            if (code != null && code.startsWith("EMP")) {
                try {
                    int number = Integer.parseInt(code.substring(3));
                    maxNumber = Math.max(maxNumber, number);
                } catch (NumberFormatException ignored) {}
            }
        }
        
        return String.format("EMP%04d", maxNumber + 1);
    }

    @Override
    public EmployeeDynamo update(String id, EmployeeDynamo employee) {
        Optional<EmployeeDynamo> existing = employeeRepo.findById(id);
        if (existing.isPresent()) {
            EmployeeDynamo existingEmployee = existing.get();
            
            // Only update fields that are not null
            if (employee.getFirstName() != null) existingEmployee.setFirstName(employee.getFirstName());
            if (employee.getLastName() != null) existingEmployee.setLastName(employee.getLastName());
            if (employee.getEmail() != null) {
                // Check email uniqueness for updates
                List<EmployeeDynamo> existingByEmail = employeeRepo.findByEmail(employee.getEmail());
                if(!existingByEmail.isEmpty() && !existingByEmail.get(0).getId().equals(existingEmployee.getId())){
                    throw new RuntimeException("Email already exists");
                }
                existingEmployee.setEmail(employee.getEmail());
            }
            if (employee.getSalary() != null) existingEmployee.setSalary(employee.getSalary());
            if (employee.getDepartmentId() != null) existingEmployee.setDepartmentId(employee.getDepartmentId());
            if (employee.getJoinDate() != null) existingEmployee.setJoinDate(employee.getJoinDate());
            if (employee.getEmployeeCode() != null) existingEmployee.setEmployeeCode(employee.getEmployeeCode());
            
            // Update timestamp
            existingEmployee.setUpdatedAt(java.time.Instant.now().toString());
            return employeeRepo.save(existingEmployee);
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
    
    @Override
    public EmployeeDynamo updateByEmployeeCode(String employeeCode, EmployeeDynamo employee) {
        EmployeeDynamo existing = findByEmployeeCode(employeeCode);
        if (existing != null) {
            return update(existing.getId(), employee);
        }
        return null;
    }
    
    @Override
    public void deleteByEmployeeCode(String employeeCode) {
        EmployeeDynamo existing = findByEmployeeCode(employeeCode);
        if (existing != null) {
            delete(existing.getId());
        }
    }

}
