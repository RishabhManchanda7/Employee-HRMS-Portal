package com.example.hrms.controller;

import com.example.hrms.entity.dynamo.AttendanceDynamo;
import com.example.hrms.service.AttendanceService;
import com.example.hrms.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    public AttendanceController(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<AttendanceDynamo> all() {
        return attendanceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDynamo> get(@PathVariable String id) {
        AttendanceDynamo a = attendanceService.getById(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttendanceDynamo a) {
        // Validate employee exists
        if (a.getEmployeeCode() == null || employeeService.findByEmployeeCode(a.getEmployeeCode()) == null) {
            return ResponseEntity.badRequest().body("Employee with code '" + a.getEmployeeCode() + "' does not exist");
        }
        return ResponseEntity.ok(attendanceService.create(a));
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulk(@RequestBody List<AttendanceDynamo> list) {
        // Validate all employees exist
        for (AttendanceDynamo a : list) {
            if (a.getEmployeeCode() == null || employeeService.findByEmployeeCode(a.getEmployeeCode()) == null) {
                return ResponseEntity.badRequest().body("Employee with code '" + a.getEmployeeCode() + "' does not exist");
            }
        }
        return ResponseEntity.ok(attendanceService.createBulk(list));
    }

    @PutMapping("/{id}")
    public AttendanceDynamo update(@PathVariable String id, @RequestBody AttendanceDynamo a) {
        return attendanceService.update(id, a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeCode}")
    public List<AttendanceDynamo> byEmployee(@PathVariable String employeeCode) {
        return attendanceService.findByEmployeeCode(employeeCode);
    }

    @GetMapping("/date/{date}")
    public List<AttendanceDynamo> byDate(@PathVariable String date) {
        return attendanceService.findByDate(date);
    }

    @GetMapping("/employee/{employeeCode}/date/{date}")
    public List<AttendanceDynamo> byEmployeeAndDate(@PathVariable String employeeCode, @PathVariable String date) {
        return attendanceService.findByEmployeeCodeAndDate(employeeCode, date);
    }
}


