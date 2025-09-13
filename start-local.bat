@echo off
echo Starting Employee HRMS Portal with DynamoDB Local...

echo.
echo Step 1: Starting DynamoDB Local...
start "DynamoDB Local" cmd /k "docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -inMemory"

echo.
echo Waiting 10 seconds for DynamoDB Local to start...
timeout /t 10 /nobreak > nul

echo.
echo Step 2: Building and starting the application...
mvn clean compile spring-boot:run

pause
