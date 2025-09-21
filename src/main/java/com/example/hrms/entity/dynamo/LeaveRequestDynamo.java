package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "leaves")
public class LeaveRequestDynamo extends BaseEntity {

    @DynamoDBHashKey
    private String id; 
    @DynamoDBAttribute
    private String employeeId;

    @DynamoDBAttribute
    private String startDate; 

    @DynamoDBAttribute
    private String endDate; 

    @DynamoDBAttribute
    private String type; // SICK, CASUAL, PTO

    @DynamoDBAttribute
    private String reason;

    @DynamoDBAttribute
    private String status; // PENDING, APPROVED, REJECTED

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


