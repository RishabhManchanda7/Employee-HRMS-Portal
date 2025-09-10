package com.example.hrms.service;

import com.example.hrms.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    Employee getById(Long id);
    Employee create(Employee e);
    Employee update(Long id, Employee e);
    void delete(Long id);
}