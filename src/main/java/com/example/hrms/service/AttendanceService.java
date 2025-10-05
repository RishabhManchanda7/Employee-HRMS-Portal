package com.example.hrms.service;

import com.example.hrms.entity.dynamo.AttendanceDynamo;

import java.util.List;

public interface AttendanceService {
    List<AttendanceDynamo> getAll();
    AttendanceDynamo getById(String id);
    AttendanceDynamo create(AttendanceDynamo attendance);
    List<AttendanceDynamo> createBulk(List<AttendanceDynamo> records);
    AttendanceDynamo update(String id, AttendanceDynamo attendance);
    void delete(String id);

    List<AttendanceDynamo> findByEmployeeCode(String employeeCode);
    List<AttendanceDynamo> findByDate(String date);
    List<AttendanceDynamo> findByEmployeeCodeAndDate(String employeeCode, String date);
}


