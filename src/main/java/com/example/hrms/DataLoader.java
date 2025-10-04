//package com.example.hrms;
//
//import com.example.hrms.entity.dynamo.DepartmentDynamo;
//import com.example.hrms.entity.dynamo.EmployeeDynamo;
//import com.example.hrms.entity.dynamo.PayrollDynamo;
//import com.example.hrms.entity.dynamo.AttendanceDynamo;
//import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
//import com.example.hrms.entity.dynamo.LeaveType;
//import com.example.hrms.entity.dynamo.LeaveStatus;
//import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
//import com.example.hrms.repository.dynamo.PayrollDynamoRepository;
//import com.example.hrms.service.DynamoDBTableService;
//import com.example.hrms.service.EmployeeService;
//import com.example.hrms.service.AttendanceService;
//import com.example.hrms.service.LeaveService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final DepartmentDynamoRepository deptRepo;
//    private final EmployeeService employeeService;
//    private final DynamoDBTableService tableService;
//    private final PayrollDynamoRepository payrollRepo;
//    private final AttendanceService attendanceService;
//    private final LeaveService leaveService;
//
//
//    public DataLoader(DepartmentDynamoRepository deptRepo, EmployeeService employeeService, DynamoDBTableService tableService, PayrollDynamoRepository payrollRepo, AttendanceService attendanceService, LeaveService leaveService) {
//        this.deptRepo = deptRepo;
//        this.employeeService = employeeService;
//        this.tableService = tableService;
//        this.payrollRepo = payrollRepo;
//        this.attendanceService = attendanceService;
//        this.leaveService = leaveService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Initialize DynamoDB tables
//        tableService.createTablesIfNotExist();
//
//
//        createDepartment("DEPT-HR", "Human Resources", "Delhi");
//        createDepartment("DEPT-IT", "Information Technology", "Bangalore");
//        createDepartment("DEPT-FIN", "Finance", "Mumbai");
//
//        // Create Employees
//        createEmployeeIfNotExists("EMP001", "Rishabh", "Manchanda", "rishabh@example.com", "35000", "DEPT-HR");
//        createEmployeeIfNotExists("EMP002", "Priya", "Sharma", "priya.sharma@example.com", "42000", "DEPT-IT");
//        createEmployeeIfNotExists("EMP003", "Amit", "Kumar", "amit.kumar@example.com", "38000", "DEPT-IT");
//        createEmployeeIfNotExists("EMP004", "Sneha", "Patel", "sneha.patel@example.com", "45000", "DEPT-FIN");
//        createEmployeeIfNotExists("EMP005", "Rajesh", "Singh", "rajesh.singh@example.com", "40000", "DEPT-HR");
//        createEmployeeIfNotExists("EMP006", "Anita", "Gupta", "anita.gupta@example.com", "48000", "DEPT-IT");
//        createEmployeeIfNotExists("EMP007", "Vikram", "Joshi", "vikram.joshi@example.com", "52000", "DEPT-FIN");
//        createEmployeeIfNotExists("EMP008", "Deepika", "Reddy", "deepika.reddy@example.com", "39000", "DEPT-HR");
//        createEmployeeIfNotExists("EMP0011", "Deepika", "Reddy", "deepika.reddy22@example.com", "39000", "DEPT-HR");
//
//
//        createPayrollRecord("EMP001", "2024-01", new BigDecimal("5000"), new BigDecimal("2000"), new BigDecimal("1000"));
//        createPayrollRecord("EMP002", "2024-01", new BigDecimal("3000"), new BigDecimal("1500"), new BigDecimal("800"));
//        createPayrollRecord("EMP003", "2024-01", new BigDecimal("4000"), new BigDecimal("1000"), new BigDecimal("600"));
//        createPayrollRecord("EMP004", "2024-01", new BigDecimal("6000"), new BigDecimal("2500"), new BigDecimal("1200"));
//        createPayrollRecord("EMP005", "2024-01", new BigDecimal("2000"), new BigDecimal("500"), new BigDecimal("400"));
//        createPayrollRecord("EMP006", "2024-01", new BigDecimal("7000"), new BigDecimal("3000"), new BigDecimal("1500"));
//        createPayrollRecord("EMP007", "2024-01", new BigDecimal("8000"), new BigDecimal("4000"), new BigDecimal("2000"));
//        createPayrollRecord("EMP008", "2024-01", new BigDecimal("1500"), new BigDecimal("300"), new BigDecimal("200"));
//
//        seedAttendance("EMP001");
//        seedAttendance("EMP002");
//        seedAttendance("EMP003");
//        seedAttendance("EMP004");
//        seedAttendance("EMP005");
//        seedAttendance("EMP006");
//        seedAttendance("EMP007");
//        seedAttendance("EMP008");
//
//        // Seed sample leave requests
//        seedLeaves();
//    }
//
//    private void createDepartment(String code, String name, String location) {
//        DepartmentDynamo dept = new DepartmentDynamo();
//        dept.setId(code);
//        dept.setDepartmentCode(code);
//        dept.setName(name);
//        dept.setLocation(location);
//        dept.setTimestamps();
//        deptRepo.save(dept);
//        System.out.println("Created department: " + name);
//    }
//
//    private void createEmployeeIfNotExists(String empCode, String firstName, String lastName, String email, String salary, String deptId) {
//        if (employeeService.findByEmployeeCode(empCode) != null) {
//            System.out.println("Employee " + empCode + " already exists, skipping");
//            return;
//        }
//        EmployeeDynamo emp = new EmployeeDynamo(empCode);
//        emp.setFirstName(firstName);
//        emp.setLastName(lastName);
//        emp.setEmail(email);
//        emp.setSalary(new BigDecimal(salary));
//        emp.setJoinDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
//        emp.setDepartmentId(deptId);
//        emp.setTimestamps();
//        employeeService.create(emp);
//        System.out.println("Created employee: " + firstName + " " + lastName);
//    }
//
//    private void createPayrollRecord(String empCode, String payPeriod, BigDecimal overtimePay, BigDecimal bonuses, BigDecimal deductions) {
//        PayrollDynamo payroll = new PayrollDynamo();
//        String payrollCode = "PAY-" + empCode + "-" + payPeriod;
//        payroll.setId(payrollCode);
//        payroll.setPayrollCode(payrollCode);
//        payroll.setEmployeeId(empCode);
//        payroll.setPayPeriod(payPeriod);
//        payroll.setOvertimePay(overtimePay);
//        payroll.setBonuses(bonuses);
//        payroll.setDeductions(deductions);
//        payroll.setPayDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
//        payroll.setStatus("COMPLETED");
//
//        // Get employee salary for base salary
//        try {
//            EmployeeDynamo emp = employeeService.findByEmployeeCode(empCode);
//            if (emp != null) {
//                payroll.setBaseSalary(emp.getSalary());
//                // Calculate net pay
//                BigDecimal netPay = emp.getSalary().add(overtimePay).add(bonuses).subtract(deductions);
//                payroll.setNetPay(netPay);
//            }
//        } catch (Exception e) {
//            System.out.println("Error getting employee for payroll: " + e.getMessage());
//        }
//
//        payroll.setTimestamps();
//        payrollRepo.save(payroll);
//        System.out.println("Created payroll record: " + payrollCode);
//    }
//
//    private void seedAttendance(String empCode) {
//        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
//        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        String twoDaysAgo = LocalDate.now().minusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE);
//
//        createAttendance(empCode, twoDaysAgo, "P");
//        createAttendance(empCode, yesterday, "P");
//        createAttendance(empCode, today, "P");
//    }
//
//    private void createAttendance(String empCode, String date, String status) {
//        AttendanceDynamo att = new AttendanceDynamo();
//        att.setEmployeeId(empCode);
//        att.setDate(date);
//        att.setStatus(status);
//        // ID will be set in service if missing: employeeId-date
//        attendanceService.create(att);
//        System.out.println("Created attendance: " + empCode + " " + date + " " + status);
//    }
//
//    // Minimal sample leave requests per first two employees
//    private void seedLeaves() {
//        createLeave("EMP001", LocalDate.now().plusDays(3), LocalDate.now().plusDays(4), LeaveType.SICK, "Fever");
//        createLeave("EMP002", LocalDate.now().plusDays(5), LocalDate.now().plusDays(7), LeaveType.PTO, "Family trip");
//    }
//
//    private void createLeave(String empId, LocalDate start, LocalDate end, LeaveType type, String reason) {
//        LeaveRequestDynamo r = new LeaveRequestDynamo();
//        r.setEmployeeId(empId);
//        r.setStartDate(start.format(DateTimeFormatter.ISO_LOCAL_DATE));
//        r.setEndDate(end.format(DateTimeFormatter.ISO_LOCAL_DATE));
//        r.setType(type);
//        r.setReason(reason);
//        r.setStatus(LeaveStatus.PENDING);
//        r.setId("LEAVE-" + empId + "-" + r.getStartDate());
//        r.setTimestamps();
//        leaveService.create(r);
//        System.out.println("Created leave request: " + r.getId());
//    }
//
//}