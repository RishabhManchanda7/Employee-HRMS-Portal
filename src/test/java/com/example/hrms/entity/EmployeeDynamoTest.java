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
        assertEquals("EMP001", employee.getEmployeeCode());
        // ID is now hash-based and hidden from JSON
        assertNotNull(employee.getId());
        assertNotEquals("EMP001", employee.getId()); // ID should be hash, not employee code
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
        assertEquals("EMP001", employee.getEmployeeCode());
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
    
    @Test
    void testHashIdGeneration() {
        EmployeeDynamo emp1 = new EmployeeDynamo("EMP001");
        EmployeeDynamo emp2 = new EmployeeDynamo("EMP002");
        
        // Different employee codes should generate different hash IDs
        assertNotEquals(emp1.getId(), emp2.getId());
        
        // Same employee code should generate same hash ID
        EmployeeDynamo emp3 = new EmployeeDynamo("EMP001");
        assertEquals(emp1.getId(), emp3.getId());
    }
}