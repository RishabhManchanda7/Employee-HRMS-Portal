package com.example.hrms.salary.DTO.service.controller;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import com.example.hrms.salary.DTO.EmployeeSalarySummary;
import com.example.hrms.salary.DTO.service.SalaryComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin(origins = "*")
public class SalaryController {
    
    @Autowired
    private SalaryComputationService salaryService;
    
    /**
     * Get salary summary for a specific employee by employee code
     */
    @GetMapping("/employee/{employeeCode}")
    public ResponseEntity<EmployeeSalarySummary> getEmployeeSalarySummary(@PathVariable String employeeCode) {
        try {
            EmployeeSalarySummary summary = salaryService.getEmployeeSalarySummary(employeeCode);
            return ResponseEntity.ok(summary);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get salary summary for a specific employee by employee ID
     */
    @GetMapping("/employee/id/{employeeId}")
    public ResponseEntity<EmployeeSalarySummary> getEmployeeSalarySummaryById(@PathVariable String employeeId) {
        try {
            EmployeeSalarySummary summary = salaryService.getEmployeeSalarySummaryById(employeeId);
            return ResponseEntity.ok(summary);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get salary summaries for all employees in a department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeSalarySummary>> getDepartmentSalarySummaries(@PathVariable String departmentId) {
        try {
            List<EmployeeSalarySummary> summaries = salaryService.getDepartmentSalarySummaries(departmentId);
            return ResponseEntity.ok(summaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get salary summaries for all employees
     */
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeSalarySummary>> getAllEmployeeSalarySummaries() {
        try {
            List<EmployeeSalarySummary> summaries = salaryService.getAllEmployeeSalarySummaries();
            return ResponseEntity.ok(summaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Create payroll record for an employee
     */
    @PostMapping("/payroll/create")
    public ResponseEntity<PayrollDynamo> createPayrollRecord(@RequestBody CreatePayrollRequest request) {
        try {
            PayrollDynamo payroll = salaryService.createPayrollRecord(
                request.getEmployeeCode(),
                request.getPayPeriod(),
                request.getOvertimePay(),
                request.getBonuses(),
                request.getDeductions()
            );
            return ResponseEntity.ok(payroll);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Update payroll record status
     */
    @PutMapping("/payroll/{payrollCode}/status")
    public ResponseEntity<PayrollDynamo> updatePayrollStatus(
            @PathVariable String payrollCode, 
            @RequestBody UpdateStatusRequest request) {
        try {
            PayrollDynamo payroll = salaryService.updatePayrollStatus(payrollCode, request.getStatus());
            return ResponseEntity.ok(payroll);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get payroll history for an employee
     */
    @GetMapping("/payroll/history/{employeeCode}")
    public ResponseEntity<List<PayrollDynamo>> getEmployeePayrollHistory(@PathVariable String employeeCode) {
        try {
            List<PayrollDynamo> history = salaryService.getEmployeePayrollHistory(employeeCode);
            return ResponseEntity.ok(history);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get department salary statistics
     */
    @GetMapping("/department/{departmentId}/stats")
    public ResponseEntity<SalaryComputationService.DepartmentSalaryStats> getDepartmentSalaryStats(@PathVariable String departmentId) {
        try {
            SalaryComputationService.DepartmentSalaryStats stats = salaryService.getDepartmentSalaryStats(departmentId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Request DTO for creating payroll records
     */
    public static class CreatePayrollRequest {
        private String employeeCode;
        private String payPeriod;
        private BigDecimal overtimePay;
        private BigDecimal bonuses;
        private BigDecimal deductions;
        
        // Getters and setters
        public String getEmployeeCode() { return employeeCode; }
        public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
        
        public String getPayPeriod() { return payPeriod; }
        public void setPayPeriod(String payPeriod) { this.payPeriod = payPeriod; }
        
        public BigDecimal getOvertimePay() { return overtimePay; }
        public void setOvertimePay(BigDecimal overtimePay) { this.overtimePay = overtimePay; }
        
        public BigDecimal getBonuses() { return bonuses; }
        public void setBonuses(BigDecimal bonuses) { this.bonuses = bonuses; }
        
        public BigDecimal getDeductions() { return deductions; }
        public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }
    }
    
    /**
     * Request DTO for updating payroll status
     */
    public static class UpdateStatusRequest {
        private String status;
        
        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
