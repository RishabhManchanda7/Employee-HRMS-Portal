package com.example.hrms.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
public class Payroll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payrollCode;
    private String payPeriod;
    private BigDecimal baseSalary;
    private BigDecimal overtimePay;
    private BigDecimal bonuses;
    private BigDecimal deductions;
    private BigDecimal netPay;
    private LocalDate payDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Payroll() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPayrollCode() { return payrollCode; }
    public void setPayrollCode(String payrollCode) { this.payrollCode = payrollCode; }

    public String getPayPeriod() { return payPeriod; }
    public void setPayPeriod(String payPeriod) { this.payPeriod = payPeriod; }

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

    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}