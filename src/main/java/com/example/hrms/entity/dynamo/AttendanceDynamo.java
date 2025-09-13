package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "attendances")
public class AttendanceDynamo extends BaseEntity {
    
    @DynamoDBAttribute
    private String date;
    
    @DynamoDBAttribute
    private String status; // "P", "A", "L"
    
    @DynamoDBAttribute
    private String employeeId;

    public AttendanceDynamo() {
        super();
    }

    public AttendanceDynamo(String id) {
        this();
        this.setId(id);
        setTimestamps();
    }

    // Getters and Setters
    @DynamoDBHashKey
    @Override
    public String getId() { return super.getId(); }
    
    @Override
    public void setId(String id) { super.setId(id); }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
