package com.example.hrms.service.impl;

import com.example.hrms.entity.Employee;
import com.example.hrms.repository.EmployeeRepository;
import com.example.hrms.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Employee> getAll() {
        return repo.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Employee create(Employee e) {
        return repo.save(e);
    }

    @Override
    public Employee update(Long id, Employee e) {
        e.setId(id);
        return repo.save(e);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
