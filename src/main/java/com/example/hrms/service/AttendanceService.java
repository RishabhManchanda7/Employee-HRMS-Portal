package com.example.hrms.service;

import com.example.hrms.entity.dynamo.AttendanceDynamo;

import java.util.List;

public interface AttendanceService {
    List<AttendanceDynamo> getAll();
    AttendanceDynamo getById(String id);
    AttendanceDynamo create(AttendanceDynamo attendance);
    AttendanceDynamo update(String id, AttendanceDynamo attendance);
    void delete(String id);

    List<AttendanceDynamo> findByEmployeeId(String employeeId);
    List<AttendanceDynamo> findByDate(String date);
    List<AttendanceDynamo> findByEmployeeIdAndDate(String employeeId, String date);
}


