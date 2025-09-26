package com.example.hrms.service;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.entity.dynamo.LeaveStatus;

import java.util.List;

public interface LeaveService {
    LeaveRequestDynamo create(LeaveRequestDynamo request) ;
    List<LeaveRequestDynamo> createBulk(List<LeaveRequestDynamo> requests);
    LeaveRequestDynamo getById(String id);
    List<LeaveRequestDynamo> getByEmployee(String employeeId);
    List<LeaveRequestDynamo> getByStatus(LeaveStatus status);
    LeaveRequestDynamo approve(String id);
    LeaveRequestDynamo reject(String id, String reason);
}


