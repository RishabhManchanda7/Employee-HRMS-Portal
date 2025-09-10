package com.example.hrms.repository;

import com.example.hrms.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Long> { }