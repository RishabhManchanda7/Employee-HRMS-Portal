package com.example.hrms.salary.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeSalarySummary {
    
    // Employee Information
    private String employeeId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String joinDate;
    
    // Department Information
    private String departmentId;
    private String departmentCode;
    private String departmentName;
    private String departmentLocation;
    
    // Salary Information
    private BigDecimal baseSalary;
    private BigDecimal overtimePay;
    private BigDecimal bonuses;
    private BigDecimal deductions;
    private BigDecimal netPay;
    
    // Payroll Information
    private String payrollCode;
    private String payPeriod;
    private String payDate;
    private String status;
    
    // Computed Fields
    private BigDecimal totalEarnings;
    private BigDecimal totalDeductions;
    private BigDecimal grossPay;
    
    public EmployeeSalarySummary() {}
    
    public EmployeeSalarySummary(String employeeId, String employeeCode, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    
    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public String getDepartmentLocation() { return departmentLocation; }
    public void setDepartmentLocation(String departmentLocation) { this.departmentLocation = departmentLocation; }
    
    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }
    
    public BigDecimal getOvertimePay() { return overtimePay; }
    public void setOvertimePay(BigDecimal overtimePay) { this.overtimePay = overtimePay; }
    
    public BigDecimal getBonuses() { return bonuses; }
    public void setBonuses(BigDecimal bonuses) { this.bonuses = bonuses; }
    
    public BigDecimal getDeductions() { return deductions; }
    public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }
    
    public BigDecimal getNetPay() { return netPay; }
    public void setNetPay(BigDecimal netPay) { this.netPay = netPay; }
    
    public String getPayrollCode() { return payrollCode; }
    public void setPayrollCode(String payrollCode) { this.payrollCode = payrollCode; }
    
    public String getPayPeriod() { return payPeriod; }
    public void setPayPeriod(String payPeriod) { this.payPeriod = payPeriod; }
    
    public String getPayDate() { return payDate; }
    public void setPayDate(String payDate) { this.payDate = payDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getTotalEarnings() { return totalEarnings; }
    public void setTotalEarnings(BigDecimal totalEarnings) { this.totalEarnings = totalEarnings; }
    
    public BigDecimal getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(BigDecimal totalDeductions) { this.totalDeductions = totalDeductions; }
    
    public BigDecimal getGrossPay() { return grossPay; }
    public void setGrossPay(BigDecimal grossPay) { this.grossPay = grossPay; }
    
    // Computed methods
    public String getFullName() {
        if (firstName == null || lastName == null) return "";
        return firstName + " " + lastName;
    }
    
    public void calculateTotals() {
        if (baseSalary == null) baseSalary = BigDecimal.ZERO;
        if (overtimePay == null) overtimePay = BigDecimal.ZERO;
        if (bonuses == null) bonuses = BigDecimal.ZERO;
        if (deductions == null) deductions = BigDecimal.ZERO;
        
        this.totalEarnings = baseSalary.add(overtimePay).add(bonuses);
        this.totalDeductions = deductions;
        this.grossPay = totalEarnings.subtract(totalDeductions);
        this.netPay = grossPay;
    }
    
    @Override
    public String toString() {
        return "EmployeeSalarySummary{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", baseSalary=" + baseSalary +
                ", netPay=" + netPay +
                '}';
    }
}
