package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

@DynamoDBTable(tableName = "leaves")
public class LeaveRequestDynamo extends BaseEntity {

    @DynamoDBHashKey
    private String id;

    public LeaveRequestDynamo() {
        super();
    }
    
    public LeaveRequestDynamo(String leaveRequestCode) {
        this();
        this.leaveRequestCode = leaveRequestCode;
        this.setId(generateHashId(leaveRequestCode));
        setTimestamps();
    }
    
    @DynamoDBAttribute
    private String leaveRequestCode; 
    @DynamoDBAttribute
    private String employeeId;

    @DynamoDBAttribute
    private String startDate; 

    @DynamoDBAttribute
    private String endDate; 

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private LeaveType type;

    @DynamoDBAttribute
    private String reason;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private LeaveStatus status;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLeaveRequestCode() { return leaveRequestCode; }
    public void setLeaveRequestCode(String leaveRequestCode) { 
        this.leaveRequestCode = leaveRequestCode;
        this.setId(generateHashId(leaveRequestCode));
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public LeaveType getType() { return type; }
    public void setType(LeaveType type) { this.type = type; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LeaveStatus getStatus() { return status; }
    public void setStatus(LeaveStatus status) { this.status = status; }
}


