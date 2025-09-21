package com.example.hrms.salary.DTO;


public class PayrollJob {
    private String employeeCode; // e.g., EMP001
    private String payPeriod;    // e.g., 2025-09

    public PayrollJob() {}

    public PayrollJob(String employeeCode, String payPeriod) {
        this.employeeCode = employeeCode;
        this.payPeriod = payPeriod;
    }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getPayPeriod() { return payPeriod; }
    public void setPayPeriod(String payPeriod) { this.payPeriod = payPeriod; }
}


