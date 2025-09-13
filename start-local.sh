#!/bin/bash

echo "Starting Employee HRMS Portal with DynamoDB Local..."

echo ""
echo "Step 1: Starting DynamoDB Local..."
docker run -d -p 8000:8000 --name dynamodb-local amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -inMemory

echo ""
echo "Waiting 10 seconds for DynamoDB Local to start..."
sleep 10

echo ""
echo "Step 2: Building and starting the application..."
mvn clean compile spring-boot:run

echo ""
echo "To stop DynamoDB Local, run: docker stop dynamodb-local && docker rm dynamodb-local"
