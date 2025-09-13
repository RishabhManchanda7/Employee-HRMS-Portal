package com.example.hrms.repository.dynamo;

import com.example.hrms.entity.dynamo.PayrollDynamo;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollDynamoRepository extends DynamoDBPagingAndSortingRepository<PayrollDynamo, String> {
    
    // Find by payroll code
    Optional<PayrollDynamo> findByPayrollCode(String payrollCode);
    
    // Find by employee ID
    List<PayrollDynamo> findByEmployeeId(String employeeId);
    
    // Find by status
    List<PayrollDynamo> findByStatus(String status);
    
    // Find by pay period
    List<PayrollDynamo> findByPayPeriod(String payPeriod);
}
