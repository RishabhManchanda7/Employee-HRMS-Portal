package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "payrolls")
public class PayrollDynamo extends BaseEntity {
    
    @DynamoDBHashKey
    private String id;
    

    
    @DynamoDBAttribute
    private String payrollCode;
    
    @DynamoDBAttribute
    private String payPeriod;
    
    @DynamoDBAttribute
    private BigDecimal baseSalary;
    
    @DynamoDBAttribute
    private BigDecimal overtimePay;
    
    @DynamoDBAttribute
    private BigDecimal bonuses;
    
    @DynamoDBAttribute
    private BigDecimal deductions;
    
    @DynamoDBAttribute
    private BigDecimal netPay;
    
    @DynamoDBAttribute
    private String payDate;
    
    @DynamoDBAttribute
    private String status;
    
    @DynamoDBAttribute
    private String employeeCode;

    public PayrollDynamo() {
        super();
    }

    public PayrollDynamo(String payrollCode) {
        this();
        this.payrollCode = payrollCode;
        this.setId(generateHashId(payrollCode));
        setTimestamps();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPayrollCode() { return payrollCode; }
    public void setPayrollCode(String payrollCode) { 
        this.payrollCode = payrollCode;
        this.setId(generateHashId(payrollCode));
    }

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

    public String getPayDate() { return payDate; }
    public void setPayDate(String payDate) { this.payDate = payDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
}
