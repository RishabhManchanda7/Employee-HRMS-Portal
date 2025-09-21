package com.example.hrms.repository.dynamo;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface LeaveRequestDynamoRepository extends DynamoDBPagingAndSortingRepository<LeaveRequestDynamo, String> {
    List<LeaveRequestDynamo> findByEmployeeId(String employeeId);
    List<LeaveRequestDynamo> findByStatus(String status);
}


