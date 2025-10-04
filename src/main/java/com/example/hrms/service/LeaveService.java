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
    LeaveRequestDynamo updateStatus(String leaveRequestCode, LeaveStatus status, String reason);
    LeaveRequestDynamo updateStatusById(String id, LeaveStatus status, String reason);
}


