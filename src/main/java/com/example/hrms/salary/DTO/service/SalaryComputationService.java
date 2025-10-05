package com.example.hrms.salary.DTO.service;

import com.example.hrms.entity.dynamo.DepartmentDynamo;
import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.repository.dynamo.DepartmentDynamoRepository;
import com.example.hrms.repository.dynamo.EmployeeDynamoRepository;
import com.example.hrms.repository.dynamo.PayrollDynamoRepository;
import com.example.hrms.salary.DTO.EmployeeSalarySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryComputationService {
    
    @Autowired
    private EmployeeDynamoRepository employeeRepository;
    
    @Autowired
    private DepartmentDynamoRepository departmentRepository;
    
    @Autowired
    private PayrollDynamoRepository payrollRepository;
    
    /**
     * Get salary summary for a specific employee by employee code
     */
    public EmployeeSalarySummary getEmployeeSalarySummary(String employeeCode) {
        Optional<EmployeeDynamo> employeeOpt = employeeRepository.findByEmployeeCode(employeeCode);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found with code: " + employeeCode);
        }
        
        EmployeeDynamo employee = employeeOpt.get();
        return buildSalarySummary(employee);
    }
    
    /**
     * Get salary summary for a specific employee by employee ID
     */
    public EmployeeSalarySummary getEmployeeSalarySummaryById(String employeeId) {
        Optional<EmployeeDynamo> employeeOpt = employeeRepository.findById(employeeId);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
        
        EmployeeDynamo employee = employeeOpt.get();
        return buildSalarySummary(employee);
    }
    
    /**
     * Get salary summaries for all employees in a department
     */
    public List<EmployeeSalarySummary> getDepartmentSalarySummaries(String departmentId) {
        List<EmployeeDynamo> employees = employeeRepository.findByDepartmentId(departmentId);
        List<EmployeeSalarySummary> summaries = new ArrayList<>();
        
        for (EmployeeDynamo employee : employees) {
            summaries.add(buildSalarySummary(employee));
        }
        
        return summaries;
    }
    
    /**
     * Get salary summaries for all employees
     */
    public List<EmployeeSalarySummary> getAllEmployeeSalarySummaries() {
        List<EmployeeDynamo> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        
        List<EmployeeSalarySummary> summaries = new ArrayList<>();
        for (EmployeeDynamo employee : employees) {
            summaries.add(buildSalarySummary(employee));
        }
        
        return summaries;
    }
    
    /**
     * Create payroll record for an employee
     */
    public PayrollDynamo createPayrollRecord(String employeeCode, String payPeriod, 
                                           BigDecimal overtimePay, BigDecimal bonuses, 
                                           BigDecimal deductions) {
        Optional<EmployeeDynamo> employeeOpt = employeeRepository.findByEmployeeCode(employeeCode);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found with code: " + employeeCode);
        }
        
        EmployeeDynamo employee = employeeOpt.get();
        
        PayrollDynamo payroll = new PayrollDynamo();
        String payrollCode = "PAY-" + employeeCode + "-" + payPeriod;
        payroll.setPayrollCode(payrollCode);
        payroll.setEmployeeCode(employee.getEmployeeCode());
        payroll.setPayPeriod(payPeriod);
        payroll.setBaseSalary(employee.getSalary());
        payroll.setOvertimePay(overtimePay != null ? overtimePay : BigDecimal.ZERO);
        payroll.setBonuses(bonuses != null ? bonuses : BigDecimal.ZERO);
        payroll.setDeductions(deductions != null ? deductions : BigDecimal.ZERO);
        payroll.setPayDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        payroll.setStatus("PENDING");
        
        // Calculate net pay
        BigDecimal netPay = employee.getSalary()
                .add(payroll.getOvertimePay())
                .add(payroll.getBonuses())
                .subtract(payroll.getDeductions());
        payroll.setNetPay(netPay);
        
        payroll.setTimestamps();
        return payrollRepository.save(payroll);
    }
    
    /**
     * Update payroll record status
     */
    public PayrollDynamo updatePayrollStatus(String payrollCode, String status) {
        Optional<PayrollDynamo> payrollOpt = payrollRepository.findById(payrollCode);
        if (!payrollOpt.isPresent()) {
            throw new RuntimeException("Payroll record not found with code: " + payrollCode);
        }
        
        PayrollDynamo payroll = payrollOpt.get();
        payroll.setStatus(status);
        payroll.setTimestamps();
        return payrollRepository.save(payroll);
    }
    
    /**
     * Get payroll history for an employee
     */
    public List<PayrollDynamo> getEmployeePayrollHistory(String employeeCode) {
        Optional<EmployeeDynamo> employeeOpt = employeeRepository.findByEmployeeCode(employeeCode);
        if (!employeeOpt.isPresent()) {
            throw new RuntimeException("Employee not found with code: " + employeeCode);
        }
        
        EmployeeDynamo employee = employeeOpt.get();
        return payrollRepository.findByEmployeeCode(employee.getEmployeeCode());
    }
    
    /**
     * Build salary summary by joining employee, department, and payroll data
     */
    private EmployeeSalarySummary buildSalarySummary(EmployeeDynamo employee) {
        EmployeeSalarySummary summary = new EmployeeSalarySummary();
        
        // Set employee information
        summary.setEmployeeId(employee.getId());
        summary.setEmployeeCode(employee.getEmployeeCode());
        summary.setFirstName(employee.getFirstName());
        summary.setLastName(employee.getLastName());
        summary.setEmail(employee.getEmail());
        summary.setJoinDate(employee.getJoinDate());
        summary.setBaseSalary(employee.getSalary());
        
        // Get department information
        if (employee.getDepartmentId() != null) {
            Optional<DepartmentDynamo> deptOpt = departmentRepository.findById(employee.getDepartmentId());
            if (deptOpt.isPresent()) {
                DepartmentDynamo department = deptOpt.get();
                summary.setDepartmentId(department.getId());
                summary.setDepartmentCode(department.getDepartmentCode());
                summary.setDepartmentName(department.getName());
                summary.setDepartmentLocation(department.getLocation());
            }
        }
        
        // Get latest payroll information
        List<PayrollDynamo> payrolls = payrollRepository.findByEmployeeCode(employee.getEmployeeCode());
        if (!payrolls.isEmpty()) {
            // Get the most recent payroll record
            PayrollDynamo latestPayroll = payrolls.stream()
                    .max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
                    .orElse(null);
            
            if (latestPayroll != null) {
                summary.setPayrollCode(latestPayroll.getPayrollCode());
                summary.setPayPeriod(latestPayroll.getPayPeriod());
                summary.setPayDate(latestPayroll.getPayDate());
                summary.setStatus(latestPayroll.getStatus());
                summary.setOvertimePay(latestPayroll.getOvertimePay());
                summary.setBonuses(latestPayroll.getBonuses());
                summary.setDeductions(latestPayroll.getDeductions());
                summary.setNetPay(latestPayroll.getNetPay());
            }
        }
        
        // Calculate totals
        summary.calculateTotals();
        
        return summary;
    }
    
    /**
     * Calculate department salary statistics
     */
    public DepartmentSalaryStats getDepartmentSalaryStats(String departmentId) {
        List<EmployeeSalarySummary> summaries = getDepartmentSalarySummaries(departmentId);
        
        if (summaries.isEmpty()) {
            return new DepartmentSalaryStats();
        }
        
        BigDecimal totalSalary = BigDecimal.ZERO;
        BigDecimal totalOvertime = BigDecimal.ZERO;
        BigDecimal totalBonuses = BigDecimal.ZERO;
        BigDecimal totalDeductions = BigDecimal.ZERO;
        BigDecimal totalNetPay = BigDecimal.ZERO;
        
        for (EmployeeSalarySummary summary : summaries) {
            if (summary.getBaseSalary() != null) totalSalary = totalSalary.add(summary.getBaseSalary());
            if (summary.getOvertimePay() != null) totalOvertime = totalOvertime.add(summary.getOvertimePay());
            if (summary.getBonuses() != null) totalBonuses = totalBonuses.add(summary.getBonuses());
            if (summary.getDeductions() != null) totalDeductions = totalDeductions.add(summary.getDeductions());
            if (summary.getNetPay() != null) totalNetPay = totalNetPay.add(summary.getNetPay());
        }
        
        int employeeCount = summaries.size();
        BigDecimal avgSalary = totalSalary.divide(BigDecimal.valueOf(employeeCount), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal avgNetPay = totalNetPay.divide(BigDecimal.valueOf(employeeCount), 2, BigDecimal.ROUND_HALF_UP);
        
        return new DepartmentSalaryStats(
            departmentId,
            summaries.get(0).getDepartmentName(),
            employeeCount,
            totalSalary,
            totalOvertime,
            totalBonuses,
            totalDeductions,
            totalNetPay,
            avgSalary,
            avgNetPay
        );
    }
    
    /**
     * Inner class for department salary statistics
     */
    public static class DepartmentSalaryStats {
        private String departmentId;
        private String departmentName;
        private int employeeCount;
        private BigDecimal totalSalary;
        private BigDecimal totalOvertime;
        private BigDecimal totalBonuses;
        private BigDecimal totalDeductions;
        private BigDecimal totalNetPay;
        private BigDecimal avgSalary;
        private BigDecimal avgNetPay;
        
        public DepartmentSalaryStats() {}
        
        public DepartmentSalaryStats(String departmentId, String departmentName, int employeeCount,
                                   BigDecimal totalSalary, BigDecimal totalOvertime, BigDecimal totalBonuses,
                                   BigDecimal totalDeductions, BigDecimal totalNetPay,
                                   BigDecimal avgSalary, BigDecimal avgNetPay) {
            this.departmentId = departmentId;
            this.departmentName = departmentName;
            this.employeeCount = employeeCount;
            this.totalSalary = totalSalary;
            this.totalOvertime = totalOvertime;
            this.totalBonuses = totalBonuses;
            this.totalDeductions = totalDeductions;
            this.totalNetPay = totalNetPay;
            this.avgSalary = avgSalary;
            this.avgNetPay = avgNetPay;
        }
        
        // Getters and setters
        public String getDepartmentId() { return departmentId; }
        public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
        
        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
        
        public int getEmployeeCount() { return employeeCount; }
        public void setEmployeeCount(int employeeCount) { this.employeeCount = employeeCount; }
        
        public BigDecimal getTotalSalary() { return totalSalary; }
        public void setTotalSalary(BigDecimal totalSalary) { this.totalSalary = totalSalary; }
        
        public BigDecimal getTotalOvertime() { return totalOvertime; }
        public void setTotalOvertime(BigDecimal totalOvertime) { this.totalOvertime = totalOvertime; }
        
        public BigDecimal getTotalBonuses() { return totalBonuses; }
        public void setTotalBonuses(BigDecimal totalBonuses) { this.totalBonuses = totalBonuses; }
        
        public BigDecimal getTotalDeductions() { return totalDeductions; }
        public void setTotalDeductions(BigDecimal totalDeductions) { this.totalDeductions = totalDeductions; }
        
        public BigDecimal getTotalNetPay() { return totalNetPay; }
        public void setTotalNetPay(BigDecimal totalNetPay) { this.totalNetPay = totalNetPay; }
        
        public BigDecimal getAvgSalary() { return avgSalary; }
        public void setAvgSalary(BigDecimal avgSalary) { this.avgSalary = avgSalary; }
        
        public BigDecimal getAvgNetPay() { return avgNetPay; }
        public void setAvgNetPay(BigDecimal avgNetPay) { this.avgNetPay = avgNetPay; }
    }
}
