package com.example.hrms.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentCode;
    private String name;
    private String location;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}
