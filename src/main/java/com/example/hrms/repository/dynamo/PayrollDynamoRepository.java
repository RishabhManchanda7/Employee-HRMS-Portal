package com.example.hrms.repository.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.hrms.entity.dynamo.PayrollDynamo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PayrollDynamoRepository {

    private final DynamoDBMapper mapper;

    public PayrollDynamoRepository(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public List<PayrollDynamo> findAll() {
        return mapper.scan(PayrollDynamo.class, new DynamoDBScanExpression());
    }

    public PayrollDynamo findByPayrollCode(String payrollCode) {
        Map<String, AttributeValue> values = new HashMap<>();
        values.put(":code", new AttributeValue().withS(payrollCode));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("payrollCode = :code")
            .withExpressionAttributeValues(values);
            
        List<PayrollDynamo> results = mapper.scan(PayrollDynamo.class, scanExpression);
        return results.isEmpty() ? null : results.get(0);
    }

    public PayrollDynamo save(PayrollDynamo payroll) {
        mapper.save(payroll);
        return payroll;
    }

    public List<PayrollDynamo> findByEmployeeCode(String employeeCode) {
        Map<String, AttributeValue> values = new HashMap<>();
        values.put(":empCode", new AttributeValue().withS(employeeCode));
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("employeeCode = :empCode")
            .withExpressionAttributeValues(values);
            
        return mapper.scan(PayrollDynamo.class, scanExpression);
    }
}