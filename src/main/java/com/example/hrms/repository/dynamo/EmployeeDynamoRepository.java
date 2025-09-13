package com.example.hrms.repository.dynamo;

import com.example.hrms.entity.dynamo.EmployeeDynamo;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface EmployeeDynamoRepository extends DynamoDBPagingAndSortingRepository<EmployeeDynamo, String> {
    
    // Find by employee code
    Optional<EmployeeDynamo> findByEmployeeCode(String employeeCode);
    
    // Find by email
    List<EmployeeDynamo> findByEmail(String email);
    
    // Find by department ID
    List<EmployeeDynamo> findByDepartmentId(String departmentId);
}
