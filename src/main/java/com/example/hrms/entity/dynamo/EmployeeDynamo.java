package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


@DynamoDBTable(tableName = "employees")
public class EmployeeDynamo extends BaseEntity {
    
    @DynamoDBHashKey
    @JsonIgnore
    private String id;
    
    @DynamoDBAttribute
    private String employeeCode;
    
    @DynamoDBAttribute
    private String firstName;
    
    @DynamoDBAttribute
    private String lastName;
    
    @DynamoDBAttribute
    private String email;
    
    @DynamoDBAttribute
    private BigDecimal salary;
    
    @DynamoDBAttribute
    private String joinDate;
    
    @DynamoDBAttribute
    private String departmentId;
    
    @DynamoDBAttribute
    @JsonIgnore
    private String createdAt;
    
    @DynamoDBAttribute
    @JsonIgnore
    private String updatedAt;

    public EmployeeDynamo() {
        super();
    }

    public EmployeeDynamo(String employeeCode) {
        this();
        this.employeeCode = employeeCode;
        this.setId(generateHashId(employeeCode));
        setTimestamps();
    }

    // Getters and Setters
    @JsonIgnore
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { 
        this.employeeCode = employeeCode;
        this.setId(generateHashId(employeeCode));
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    @DynamoDBIgnore
    public String getFullName() {
        if (firstName == null || lastName == null) return "";
        return firstName + " " + lastName;
    }
}
