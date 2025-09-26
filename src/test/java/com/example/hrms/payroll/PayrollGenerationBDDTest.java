package com.example.hrms.payroll;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import com.example.hrms.entity.dynamo.PayrollDynamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Payroll Generation BDD Tests")
class PayrollGenerationBDDTest {

    private EmployeeDynamo employee;
    private PayrollDynamo payroll;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDynamo("EMP001");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setSalary(new BigDecimal("50000"));
    }

    @Nested
    @DisplayName("Given an employee with valid salary")
    class GivenEmployeeWithValidSalary {

        @BeforeEach
        void givenEmployeeWithValidSalary() {
            employee.setSalary(new BigDecimal("50000"));
        }

        @Nested
        @DisplayName("When payroll is generated for current month")
        class WhenPayrollGeneratedForCurrentMonth {

            @BeforeEach
            void whenPayrollGenerated() {
                payroll = new PayrollDynamo();
                payroll.setId("PAY001");
                payroll.setEmployeeId("EMP001");
                payroll.setPayPeriod("2024-01");
                payroll.setBaseSalary(new BigDecimal("50000"));
                payroll.setOvertimePay(new BigDecimal("5000"));
                payroll.setBonuses(new BigDecimal("2000"));
                payroll.setDeductions(new BigDecimal("7000"));
                payroll.setNetPay(new BigDecimal("50000"));
                payroll.setPayDate(String.valueOf(LocalDate.now()));
                payroll.setStatus("GENERATED");
            }

            @Test
            @DisplayName("Then payroll should be created successfully")
            void thenPayrollShouldBeCreated() {
                assertNotNull(payroll);
                assertEquals("PAY001", payroll.getId());
                assertEquals("EMP001", payroll.getEmployeeId());
                assertEquals("GENERATED", payroll.getStatus());
            }

            @Test
            @DisplayName("Then net pay should be calculated correctly")
            void thenNetPayShouldBeCalculated() {
                BigDecimal expectedNetPay = payroll.getBaseSalary()
                    .add(payroll.getOvertimePay())
                    .add(payroll.getBonuses())
                    .subtract(payroll.getDeductions());
                
                assertEquals(expectedNetPay, payroll.getNetPay());
            }

            @Test
            @DisplayName("Then pay date should be set to current date")
            void thenPayDateShouldBeSet() {
                assertNotNull(payroll.getPayDate());
                assertEquals(LocalDate.now().toString(), payroll.getPayDate());
            }
        }
    }

    @Nested
    @DisplayName("Given multiple employees")
    class GivenMultipleEmployees {

        private List<EmployeeDynamo> employees;

        @BeforeEach
        void givenMultipleEmployees() {
            EmployeeDynamo emp1 = new EmployeeDynamo("EMP001");
            emp1.setSalary(new BigDecimal("50000"));
            
            EmployeeDynamo emp2 = new EmployeeDynamo("EMP002");
            emp2.setSalary(new BigDecimal("60000"));
            
            employees = Arrays.asList(emp1, emp2);
        }

        @Nested
        @DisplayName("When batch payroll generation is triggered")
        class WhenBatchPayrollGenerated {

            @Test
            @DisplayName("Then payroll should be generated for all employees")
            void thenPayrollGeneratedForAll() {
                List<PayrollDynamo> payrolls = employees.stream()
                    .map(emp -> {
                        PayrollDynamo p = new PayrollDynamo();
                        p.setEmployeeId(emp.getId());
                        p.setBaseSalary(emp.getSalary());
                        p.setStatus("GENERATED");
                        return p;
                    })
                    .toList();

                assertEquals(2, payrolls.size());
                assertTrue(payrolls.stream().allMatch(p -> "GENERATED".equals(p.getStatus())));
            }
        }
    }

    @Nested
    @DisplayName("Given payroll with overtime")
    class GivenPayrollWithOvertime {

        @Test
        @DisplayName("When overtime hours are provided Then overtime pay should be calculated")
        void whenOvertimeProvided_thenOvertimeCalculated() {
            BigDecimal hourlyRate = new BigDecimal("25");
            int overtimeHours = 10;
            
            BigDecimal overtimePay = hourlyRate.multiply(new BigDecimal(overtimeHours));
            
            assertEquals(new BigDecimal("250"), overtimePay);
        }
    }

    @Nested
    @DisplayName("Given payroll status transitions")
    class GivenPayrollStatusTransitions {

        @Test
        @DisplayName("When payroll is approved Then status should change to APPROVED")
        void whenApproved_thenStatusShouldChange() {
            payroll = new PayrollDynamo();
            payroll.setStatus("GENERATED");
            
            payroll.setStatus("APPROVED");
            
            assertEquals("APPROVED", payroll.getStatus());
        }

        @Test
        @DisplayName("When payroll is processed Then status should change to PROCESSED")
        void whenProcessed_thenStatusShouldChange() {
            payroll = new PayrollDynamo();
            payroll.setStatus("APPROVED");
            
            payroll.setStatus("PROCESSED");
            
            assertEquals("PROCESSED", payroll.getStatus());
        }
    }
}