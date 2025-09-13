# Employee HRMS Portal - Local Setup Guide with NoSQL Workbench

## Prerequisites

1. **Java 11** or higher
2. **Maven 3.6** or higher
3. **DynamoDB Local** (for local development)
4. **NoSQL Workbench** (for data visualization)

## Step 1: Install DynamoDB Local

### Option A: Using Docker (Recommended)
```bash
# Pull DynamoDB Local image
docker pull amazon/dynamodb-local

# Run DynamoDB Local
docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -inMemory
```

### Option B: Download JAR file
1. Download DynamoDB Local from: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html
2. Extract the files
3. Run: `java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -port 8000`

## Step 2: Install NoSQL Workbench

1. Download NoSQL Workbench from: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/workbench.settingup.html
2. Install and launch NoSQL Workbench

## Step 3: Configure NoSQL Workbench

1. **Open NoSQL Workbench**
2. **Add a new connection:**
   - Click "Add connection"
   - Connection name: `Local DynamoDB`
   - Endpoint: `http://localhost:8000`
   - Region: `us-east-1`
   - Access key: `fake` (or any value)
   - Secret key: `fake` (or any value)
3. **Test the connection** - it should connect successfully

## Step 4: Run the Application

### Build the project
```bash
cd "Employee HRMS Portal"
mvn clean compile
```

### Run the application
```bash
mvn spring-boot:run
```

The application will:
- Start on port 8080
- Automatically create 4 DynamoDB tables:
  - `employees`
  - `departments` 
  - `payrolls`
  - `attendances`
- Load sample data (Department: DEPT-HR, Employee: EMP001)

## Step 5: Verify with NoSQL Workbench

1. **Refresh the connection** in NoSQL Workbench
2. **View the tables:**
   - You should see 4 tables: `employees`, `departments`, `payrolls`, `attendances`
3. **Browse data:**
   - Click on `employees` table
   - Click "Browse items" to see the sample employee data
   - Click on `departments` table to see the sample department data

## Step 6: Test the API

### Using curl or Postman:

```bash
# Get all employees
curl http://localhost:8080/api/employees

# Get specific employee
curl http://localhost:8080/api/employees/EMP001

# Create new employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "id": "EMP002",
    "employeeCode": "EMP002",
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@company.com",
    "salary": 60000,
    "joinDate": "2024-02-01",
    "departmentId": "DEPT-HR"
  }'

# Update employee
curl -X PUT http://localhost:8080/api/employees/EMP002 \
  -H "Content-Type: application/json" \
  -d '{
    "id": "EMP002",
    "employeeCode": "EMP002",
    "firstName": "Jane",
    "lastName": "Smith-Updated",
    "email": "jane.smith@company.com",
    "salary": 65000,
    "joinDate": "2024-02-01",
    "departmentId": "DEPT-HR"
  }'

# Delete employee
curl -X DELETE http://localhost:8080/api/employees/EMP002
```

## Step 7: Monitor Data in NoSQL Workbench

1. **After each API call**, refresh the table view in NoSQL Workbench
2. **See real-time changes** as you create, update, or delete employees
3. **Explore the data structure** - notice how DynamoDB stores the data differently than MySQL

## Troubleshooting

### DynamoDB Local not starting
```bash
# Check if port 8000 is available
netstat -an | grep 8000

# If port is busy, use different port
docker run -p 8001:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -inMemory
# Then update application.properties: aws.dynamodb.endpoint=http://localhost:8001
```

### NoSQL Workbench connection issues
- Ensure DynamoDB Local is running first
- Check the endpoint URL (should be `http://localhost:8000`)
- Verify the region is `us-east-1`

### Application startup issues
- Check Java version: `java -version`
- Check Maven version: `mvn -version`
- Ensure DynamoDB Local is running before starting the application

## Data Structure in DynamoDB

### Employees Table
```json
{
  "id": "EMP001",
  "employeeCode": "EMP001",
  "firstName": "Rishabh",
  "lastName": "Manchanda",
  "email": "rishabh@example.com",
  "salary": 35000,
  "joinDate": "2024-01-15",
  "departmentId": "DEPT-HR",
  "createdAt": "2024-01-15T10:00:00Z",
  "updatedAt": "2024-01-15T10:00:00Z"
}
```

### Departments Table
```json
{
  "id": "DEPT-HR",
  "departmentCode": "DEPT-HR",
  "name": "HR",
  "location": "Delhi",
  "createdAt": "2024-01-15T10:00:00Z",
  "updatedAt": "2024-01-15T10:00:00Z"
}
```

## Next Steps

1. **Explore the data** in NoSQL Workbench
2. **Test all API endpoints** to ensure they work correctly
3. **Add more sample data** through the API
4. **Experiment with queries** in NoSQL Workbench
5. **Compare** the DynamoDB structure with your original MySQL design

## Benefits of This Setup

✅ **Local Development**: No AWS account needed  
✅ **Visual Data Management**: NoSQL Workbench provides a GUI  
✅ **Real-time Monitoring**: See data changes as they happen  
✅ **Easy Testing**: Simple API testing with curl/Postman  
✅ **Cost-Free**: Everything runs locally  
