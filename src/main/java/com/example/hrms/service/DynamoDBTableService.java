package com.example.hrms.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DynamoDBTableService {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    public void createTablesIfNotExist() {
        createTableIfNotExists("employees");
        createTableIfNotExists("departments");
        createTableIfNotExists("payrolls");
        createTableIfNotExists("attendances");
    }

    private void createTableIfNotExists(String tableName) {
        try {
            // Check if table exists
            DescribeTableRequest describeRequest = new DescribeTableRequest()
                    .withTableName(tableName);
            amazonDynamoDB.describeTable(describeRequest);
            System.out.println("Table " + tableName + " already exists.");
        } catch (ResourceNotFoundException e) {
            // Table doesn't exist, create it
            System.out.println("Creating table " + tableName + "...");
            
            CreateTableRequest createRequest = new CreateTableRequest()
                    .withTableName(tableName)
                    .withAttributeDefinitions(
                            new AttributeDefinition("id", ScalarAttributeType.S)
                    )
                    .withKeySchema(
                            new KeySchemaElement("id", KeyType.HASH)
                    )
                    .withBillingMode(BillingMode.PAY_PER_REQUEST);

            try {
                CreateTableResult result = amazonDynamoDB.createTable(createRequest);
                System.out.println("Table " + tableName + " created successfully.");
                
                // Wait for table to be active
                waitForTableToBecomeActive(tableName);
            } catch (Exception ex) {
                System.err.println("Error creating table " + tableName + ": " + ex.getMessage());
            }
        }
    }

    private void waitForTableToBecomeActive(String tableName) {
        try {
            DescribeTableRequest describeRequest = new DescribeTableRequest()
                    .withTableName(tableName);
            
            while (true) {
                DescribeTableResult result = amazonDynamoDB.describeTable(describeRequest);
                String status = result.getTable().getTableStatus();
                
                if ("ACTIVE".equals(status)) {
                    System.out.println("Table " + tableName + " is now active.");
                    break;
                }
                
                System.out.println("Waiting for table " + tableName + " to become active. Current status: " + status);
                Thread.sleep(2000); // Wait 2 seconds before checking again
            }
        } catch (Exception e) {
            System.err.println("Error waiting for table to become active: " + e.getMessage());
        }
    }
}
