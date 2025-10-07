
package com.example.hrms.service.impl;

import com.example.hrms.entity.dynamo.AttendanceDynamo;
import com.example.hrms.repository.dynamo.AttendanceDynamoRepository;
import com.example.hrms.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceDynamoService implements AttendanceService {

    private final AttendanceDynamoRepository attendanceRepo;

    public AttendanceDynamoService(AttendanceDynamoRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    @Override
    public List<AttendanceDynamo> getAll() {
        List<AttendanceDynamo> list = new ArrayList<>();
        attendanceRepo.findAll().forEach(list::add);
        return list;
    }

    @Override
    public AttendanceDynamo getById(String id) {
        return attendanceRepo.findById(id).orElse(null);
    }

    @Override
    public AttendanceDynamo create(AttendanceDynamo attendance) {
        // Ensure employeeCode is set
        if (attendance.getEmployeeCode() == null || attendance.getEmployeeCode().isEmpty()) {
            throw new RuntimeException("Employee code is required");
        }
        
        if (attendance.getId() == null || attendance.getId().isEmpty()) {
            attendance.setId(attendance.generateHashId(attendance.getEmployeeCode() + "-" + attendance.getDate()));
        }
        attendance.setTimestamps();
        return attendanceRepo.save(attendance);
    }

    @Override
    public List<AttendanceDynamo> createBulk(List<AttendanceDynamo> records) {
        List<AttendanceDynamo> saved = new ArrayList<>();
        if (records == null) return saved;
        for (AttendanceDynamo r : records) {
            // Ensure employeeCode is set
            if (r.getEmployeeCode() == null || r.getEmployeeCode().isEmpty()) {
                throw new RuntimeException("Employee code is required for all records");
            }
            
            if (r.getId() == null || r.getId().isEmpty()) {
                r.setId(r.generateHashId(r.getEmployeeCode() + "-" + r.getDate()));
            }
            r.setTimestamps();
            saved.add(attendanceRepo.save(r));
        }
        return saved;
    }

    @Override
    public AttendanceDynamo update(String id, AttendanceDynamo attendance) {
        Optional<AttendanceDynamo> existing = attendanceRepo.findById(id);
        if (existing.isPresent()) {
            attendance.setId(id);
            attendance.setTimestamps();
            return attendanceRepo.save(attendance);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        attendanceRepo.deleteById(id);
    }

    @Override
    public List<AttendanceDynamo> findByEmployeeCode(String employeeCode) {
        return attendanceRepo.findByEmployeeCode(employeeCode);
    }

    @Override
    public List<AttendanceDynamo> findByDate(String date) {
        return attendanceRepo.findByDate(date);
    }

    @Override
    public List<AttendanceDynamo> findByEmployeeCodeAndDate(String employeeCode, String date) {
        return attendanceRepo.findByEmployeeCodeAndDate(employeeCode, date);
    }
}


