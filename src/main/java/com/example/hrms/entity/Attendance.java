package com.example.hrms.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendances")
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String status; // "P", "A", "L"

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Attendance() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}