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
        
        // Create Departments
        createDepartment("DEPT-HR", "Human Resources", "Delhi");
        createDepartment("DEPT-IT", "Information Technology", "Bangalore");
        createDepartment("DEPT-FIN", "Finance", "Mumbai");

        // Create Employees
        createEmployee("EMP001", "Rishabh", "Manchanda", "rishabh@example.com", "35000", "DEPT-HR");
        createEmployee("EMP002", "Priya", "Sharma", "priya.sharma@example.com", "42000", "DEPT-IT");
        createEmployee("EMP003", "Amit", "Kumar", "amit.kumar@example.com", "38000", "DEPT-IT");
        createEmployee("EMP004", "Sneha", "Patel", "sneha.patel@example.com", "45000", "DEPT-FIN");
        createEmployee("EMP005", "Rajesh", "Singh", "rajesh.singh@example.com", "40000", "DEPT-HR");
        createEmployee("EMP006", "Anita", "Gupta", "anita.gupta@example.com", "48000", "DEPT-IT");
        createEmployee("EMP007", "Vikram", "Joshi", "vikram.joshi@example.com", "52000", "DEPT-FIN");
        createEmployee("EMP008", "Deepika", "Reddy", "deepika.reddy@example.com", "39000", "DEPT-HR");
    }

    private void createDepartment(String code, String name, String location) {
        DepartmentDynamo dept = new DepartmentDynamo();
        dept.setId(code);
        dept.setDepartmentCode(code);
        dept.setName(name);
        dept.setLocation(location);
        dept.setTimestamps();
        deptRepo.save(dept);
        System.out.println("Created department: " + name);
    }

    private void createEmployee(String empCode, String firstName, String lastName, String email, String salary, String deptId) {
        EmployeeDynamo emp = new EmployeeDynamo();
        emp.setId(empCode);
        emp.setEmployeeCode(empCode);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setEmail(email);
        emp.setSalary(new BigDecimal(salary));
        emp.setJoinDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        emp.setDepartmentId(deptId);
        emp.setTimestamps();
        employeeService.create(emp);
        System.out.println("Created employee: " + firstName + " " + lastName);
    }
}