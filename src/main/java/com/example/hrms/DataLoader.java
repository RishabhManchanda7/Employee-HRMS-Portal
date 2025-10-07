package com.example.hrms;

import com.example.hrms.entity.dynamo.DepartmentDynamo;
import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.entity.dynamo.AttendanceDynamo;
import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.entity.dynamo.LeaveType;
import com.example.hrms.entity.dynamo.LeaveStatus;
import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
import com.example.hrms.repository.dynamo.PayrollDynamoRepository;
import com.example.hrms.service.DynamoDBTableService;
import com.example.hrms.service.EmployeeService;
import com.example.hrms.service.AttendanceService;
import com.example.hrms.service.LeaveService;
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
    private final PayrollDynamoRepository payrollRepo;
    private final AttendanceService attendanceService;
    private final LeaveService leaveService;


    public DataLoader(DepartmentDynamoRepository deptRepo, EmployeeService employeeService, DynamoDBTableService tableService, PayrollDynamoRepository payrollRepo, AttendanceService attendanceService, LeaveService leaveService) {
        this.deptRepo = deptRepo;
        this.employeeService = employeeService;
        this.tableService = tableService;
        this.payrollRepo = payrollRepo;
        this.attendanceService = attendanceService;
        this.leaveService = leaveService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize DynamoDB tables only - no sample data loading
        System.out.println("Creating DynamoDB tables if they don't exist...");
        tableService.createTablesIfNotExist();
        System.out.println("Table initialization complete. Use Postman/API to add data.");
        
        // All sample data creation is disabled
        // Use REST API endpoints to create data manually
    }



}