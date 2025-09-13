package com.example.hrms;

import com.example.hrms.entity.dynamo.DepartmentDynamo;
import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
import com.example.hrms.service.DynamoDBTableService;
import com.example.hrms.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataLoader implements CommandLineRunner {

    private final DepartmentDynamoRepository deptRepo;
    private final EmployeeService employeeService;
    private final DynamoDBTableService tableService;

    public DataLoader(DepartmentDynamoRepository deptRepo, EmployeeService employeeService, DynamoDBTableService tableService) {
        this.deptRepo = deptRepo;
        this.employeeService = employeeService;
        this.tableService = tableService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize DynamoDB tables
        tableService.createTablesIfNotExist();
        
        // Create Department
        DepartmentDynamo d = new DepartmentDynamo();
        d.setId("DEPT-HR");
        d.setDepartmentCode("DEPT-HR");
        d.setName("HR");
        d.setLocation("Delhi");
        d.setTimestamps();
        deptRepo.save(d);


        EmployeeDynamo e = new EmployeeDynamo();
        e.setId("EMP001");
        e.setEmployeeCode("EMP001");
        e.setFirstName("Rishabh");
        e.setLastName("Manchanda");
        e.setEmail("rishabh@example.com");
        e.setSalary(new BigDecimal("35000"));
        e.setJoinDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        e.setDepartmentId("DEPT-HR");
        e.setTimestamps();
        employeeService.create(e);
    }
}