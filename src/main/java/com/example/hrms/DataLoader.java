package com.example.hrms;

import com.example.hrms.entity.Department;
import com.example.hrms.entity.Employee;
import com.example.hrms.repository.DepartmentRepository;
import com.example.hrms.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartmentRepository deptRepo;
    private final EmployeeRepository empRepo;

    public DataLoader(DepartmentRepository deptRepo, EmployeeRepository empRepo) {
        this.deptRepo = deptRepo;
        this.empRepo = empRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Department d = new Department();
        d.setDepartmentCode("DEPT-HR");
        d.setName("HR");
        d.setLocation("Delhi");
        deptRepo.save(d);

        Employee e = new Employee();
        e.setEmployeeCode("EMP001");
        e.setFirstName("Rishabh");
        e.setLastName("Manchanda");
        e.setEmail("rishabh@example.com");
        e.setSalary(new BigDecimal("35000"));
        e.setJoinDate(LocalDate.now());
        e.setDepartment(d);
        empRepo.save(e);
    }
}