package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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
    private String employeeId;

    public AttendanceDynamo() {
        super();
    }

    public AttendanceDynamo(String attendanceKey) {
        this();
        this.setId(generateHashId(attendanceKey));
        setTimestamps();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
