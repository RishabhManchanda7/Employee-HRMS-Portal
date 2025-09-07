CREATE DATABASE IF NOT EXISTS employee_payroll;
USE employee_payroll;

-- Create Enums as tables
CREATE TABLE employee_status (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(20) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE payroll_status (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(20) NOT NULL UNIQUE,
    description TEXT
);

-- Insert enum values
INSERT INTO employee_status (status_name, description) VALUES
('ACTIVE', 'Employee is currently active'),
('INACTIVE', 'Employee is inactive'),
('ON_LEAVE', 'Employee is on approved leave'),
('TERMINATED', 'Employee has been terminated');

INSERT INTO payroll_status (status_name, description) VALUES
('PENDING', 'Payroll is pending processing'),
('PROCESSED', 'Payroll has been processed'),
('PAID', 'Payment has been made'),
('FAILED', 'Payment processing failed');

-- Create Employee table
CREATE TABLE employees (
    employee_id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    hire_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    position VARCHAR(100),
    status_id INT NOT NULL,
    department_id VARCHAR(50),
    manager_id VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (status_id) REFERENCES employee_status(status_id),
    FOREIGN KEY (manager_id) REFERENCES employees(employee_id)
);

-- Create Payroll table
CREATE TABLE payroll (
    payroll_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL,
    pay_period VARCHAR(20) NOT NULL,
    base_salary DECIMAL(10,2) NOT NULL,
    overtime_pay DECIMAL(10,2) DEFAULT 0.00,
    bonuses DECIMAL(10,2) DEFAULT 0.00,
    deductions DECIMAL(10,2) DEFAULT 0.00,
    net_pay DECIMAL(10,2) NOT NULL,
    pay_date DATE NOT NULL,
    status_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES payroll_status(status_id)
);

-- Add data validation constraints
ALTER TABLE employees ADD CONSTRAINT chk_salary CHECK (salary > 0);
ALTER TABLE employees ADD CONSTRAINT chk_hire_date CHECK (hire_date <= CURDATE());
ALTER TABLE payroll ADD CONSTRAINT chk_net_pay CHECK (net_pay >= 0);
ALTER TABLE payroll ADD CONSTRAINT chk_base_salary CHECK (base_salary > 0);
ALTER TABLE payroll ADD CONSTRAINT chk_overtime_pay CHECK (overtime_pay >= 0);
ALTER TABLE payroll ADD CONSTRAINT chk_bonuses CHECK (bonuses >= 0);
ALTER TABLE payroll ADD CONSTRAINT chk_deductions CHECK (deductions >= 0);

-- Create indexes for better performance
CREATE INDEX idx_employee_status ON employees(status_id);
CREATE INDEX idx_employee_manager ON employees(manager_id);
CREATE INDEX idx_employee_department ON employees(department_id);
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_employee_hire_date ON employees(hire_date);
CREATE INDEX idx_payroll_employee ON payroll(employee_id);
CREATE INDEX idx_payroll_status ON payroll(status_id);
CREATE INDEX idx_payroll_date ON payroll(pay_date);
CREATE INDEX idx_payroll_period ON payroll(pay_period);