package com.example.hrms.entity;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;

class EmployeeDynamoTest {

    private EmployeeDynamo employee;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDynamo("EMP001");
    }

    @Test
    void testEmployeeCreation() {
        assertNotNull(employee);
        assertEquals("EMP001", employee.getId());
        assertEquals("EMP001", employee.getEmployeeCode());
    }

    @Test
    void testSetEmployeeDetails() {
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setEmail("john@company.com");
        employee.setSalary(new BigDecimal("75000"));
        employee.setDepartmentId("DEPT-IT");

        assertEquals("John", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("john@company.com", employee.getEmail());
        assertEquals(new BigDecimal("75000"), employee.getSalary());
        assertEquals("DEPT-IT", employee.getDepartmentId());
    }

    @Test
    void testFullName() {
        employee.setFirstName("John");
        employee.setLastName("Smith");
        
        assertEquals("John Smith", employee.getFullName());
    }

    @Test
    void testTimestamps() {
        employee.setTimestamps();
        
        assertNotNull(employee.getCreatedAt());
        assertNotNull(employee.getUpdatedAt());
        // Check timestamps are valid ISO strings
        assertTrue(employee.getCreatedAt().contains("T"));
        assertTrue(employee.getUpdatedAt().contains("T"));
    }
}