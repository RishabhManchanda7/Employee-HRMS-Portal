package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

@DynamoDBTable(tableName = "attendances")
public class AttendanceDynamo extends BaseEntity {
    
    @DynamoDBHashKey
    private String id;
    
    @DynamoDBAttribute
    private String date;
    
    @DynamoDBAttribute
    private String status; // "P", "A", "L"
    
    @DynamoDBAttribute
    @JsonAlias("employeeId")
    private String employeeCode;

    public AttendanceDynamo() {
        super();
    }

    public AttendanceDynamo(String employeeCode, String date) {
        this();
        this.employeeCode = employeeCode;
        this.date = date;
        this.setId(generateHashId(employeeCode + "-" + date));
        setTimestamps();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
}
