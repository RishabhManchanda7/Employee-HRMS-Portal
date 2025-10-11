package com.example.hrms;


import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
import com.example.hrms.repository.dynamo.PayrollDynamoRepository;
import com.example.hrms.service.DynamoDBTableService;
import com.example.hrms.service.EmployeeService;
import com.example.hrms.service.AttendanceService;
import com.example.hrms.service.LeaveService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



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
        tableService.createTablesIfNotExist();

        // All sample data creation is disabled
        // Use REST API endpoints to create data manually
    }



}