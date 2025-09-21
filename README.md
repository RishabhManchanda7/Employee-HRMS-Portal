# Employee HRMS Portal - DynamoDB Migration 

This project has been migrated from MySQL/H2 to AWS DynamoDB using a **simplified multi-table approach** that closely mirrors your original MySQL design.

## Architecture Changes

### Before (MySQL/H2)
- Used JPA/Hibernate with H2 in-memory database
- Traditional relational database with foreign key relationships
- Multiple tables: employees, departments, payroll, attendance

### After (DynamoDB - Simplified)
- **Multi-table design** - one DynamoDB table per entity (just like MySQL)
- Simple primary keys using entity IDs
- No complex GSI patterns - much easier to understand
- Direct mapping from MySQL structure to DynamoDB

## Key Features

- **Multi-Table Design**: Each entity has its own DynamoDB table (employees, departments, payrolls, attendances)
- **Simple Primary Keys**: Uses simple `id` field as primary key (just like MySQL)
- **No Complex GSI**: No Global Secondary Indexes - much simpler than single-table design
- **MySQL-like Structure**: Very similar to your original MySQL design
- **Backward Compatibility**: All existing REST API endpoints work unchanged
- **Easy Migration**: Direct mapping from MySQL entities to DynamoDB entities

## Prerequisites

1. **Java 11** or higher
2. **Maven 3.6** or higher
3. **DynamoDB Local** (for local development)

## Setup Instructions

### 1. Install DynamoDB Local

```bash
# Download DynamoDB Local
wget https://s3-us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz
tar -xzf dynamodb_local_latest.tar.gz

# Start DynamoDB Local
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -port 8000
```

### 2. Run the Application

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will:
- Automatically create the DynamoDB table if it doesn't exist
- Load sample data (Department and Employee)
- Start on port 8080

## API Endpoints

All existing REST endpoints remain unchanged:

- `GET /api/employees` - Get all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create new employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

## Data Model

### DynamoDB Table Structure

**Tables**: 
- `employees` - Employee data
- `departments` - Department data  
- `payrolls` - Payroll data
- `attendances` - Attendance data

**Primary Key** (for all tables):
- `id` (String) - Simple primary key, just like MySQL

### Entity Structure

**Employee** (`employees` table):
- `id`: Employee code (e.g., "EMP001")
- `employeeCode`: Employee code
- `firstName`: First name
- `lastName`: Last name
- `email`: Email address
- `salary`: Salary amount
- `joinDate`: Join date
- `departmentId`: Foreign key to department

**Department** (`departments` table):
- `id`: Department code (e.g., "DEPT-HR")
- `departmentCode`: Department code
- `name`: Department name
- `location`: Department location

**Payroll** (`payrolls` table):
- `id`: Payroll code (e.g., "PAY001")
- `payrollCode`: Payroll code
- `payPeriod`: Pay period
- `baseSalary`: Base salary
- `overtimePay`: Overtime pay
- `bonuses`: Bonuses
- `deductions`: Deductions
- `netPay`: Net pay
- `payDate`: Pay date
- `status`: Payroll status
- `employeeId`: Foreign key to employee

**Attendance** (`attendances` table):
- `id`: Attendance ID
- `date`: Attendance date
- `status`: Attendance status (P/A/L)
- `employeeId`: Foreign key to employee

## Configuration

The application is configured to use DynamoDB Local by default. To use AWS DynamoDB, update `application.properties`:

```properties
# AWS DynamoDB Configuration
aws.dynamodb.endpoint=https://dynamodb.us-east-1.amazonaws.com
aws.dynamodb.region=us-east-1
aws.access.key=YOUR_ACCESS_KEY
aws.secret.key=YOUR_SECRET_KEY
```

## Migration Benefits

1. **Scalability**: DynamoDB automatically scales based on demand
2. **Performance**: Single-digit millisecond latency
3. **Cost-Effective**: Pay-per-request pricing model
4. **No Maintenance**: Fully managed service
5. **High Availability**: Built-in replication across multiple AZs
6. **Simple Structure**: Multi-table design is much easier to understand than single-table patterns
7. **MySQL-like**: Very similar to your original MySQL structure

## Code Structure

```
src/main/java/com/example/hrms/
├── config/
│   └── DynamoDBConfig.java          # DynamoDB configuration
├── entity/
│   └── dynamo/                      # DynamoDB entities
│       ├── BaseEntity.java          # Simple base with just id, timestamps
│       ├── EmployeeDynamo.java      # @DynamoDBTable(tableName = "employees")
│       ├── DepartmentDynamo.java    # @DynamoDBTable(tableName = "departments")
│       ├── PayrollDynamo.java       # @DynamoDBTable(tableName = "payrolls")
│       └── AttendanceDynamo.java    # @DynamoDBTable(tableName = "attendances")
├── repository/
│   └── dynamo/                      # DynamoDB repositories
│       ├── EmployeeDynamoRepository.java
│       ├── DepartmentDynamoRepository.java
│       ├── PayrollDynamoRepository.java
│       └── AttendanceDynamoRepository.java
├── service/
│   ├── impl/
│   │   └── EmployeeDynamoService.java    # DynamoDB service implementation
│   ├── EmployeeService.java             # Service interface
│   └── DynamoDBTableService.java        # Multi-table management
└── controller/
    └── EmployeeController.java           # REST API controller
```

## Testing

The application maintains full backward compatibility. All existing tests should work without modification, as the service layer abstracts the database implementation.

## Troubleshooting

1. **DynamoDB Local not running**: Ensure DynamoDB Local is running on port 8003
2. **Table creation issues**: Check AWS credentials and permissions
3. **Connection errors**: Verify the endpoint URL in application.properties

## Future Enhancements

- Add more complex query patterns using GSI
- Implement batch operations for better performance
- Add DynamoDB Streams for real-time data processing
- Implement caching layer for frequently accessed data


