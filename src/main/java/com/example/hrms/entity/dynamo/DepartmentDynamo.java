package com.example.hrms.entity.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "departments")
public class DepartmentDynamo extends BaseEntity {
    
    @DynamoDBAttribute
    private String departmentCode;
    
    @DynamoDBAttribute
    private String name;
    
    @DynamoDBAttribute
    private String location;

    public DepartmentDynamo() {
        super();
    }

    public DepartmentDynamo(String departmentCode) {
        this();
        this.departmentCode = departmentCode;
        this.setId(departmentCode);
        setTimestamps();
    }

    // Getters and Setters
    @DynamoDBHashKey
    @Override
    public String getId() { return super.getId(); }
    
    @Override
    public void setId(String id) { super.setId(id); }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { 
        this.departmentCode = departmentCode;
        this.setId(departmentCode);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
