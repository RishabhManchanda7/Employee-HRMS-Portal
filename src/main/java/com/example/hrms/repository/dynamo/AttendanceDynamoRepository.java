package com.example.hrms.repository.dynamo;

import com.example.hrms.entity.dynamo.AttendanceDynamo;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface AttendanceDynamoRepository extends DynamoDBPagingAndSortingRepository<AttendanceDynamo, String> {


    List<AttendanceDynamo> findByEmployeeCode(String employeeCode);
    
    // Find by date
    List<AttendanceDynamo> findByDate(String date);
    
    // Find by status
    List<AttendanceDynamo> findByStatus(String status);
    
    // Find by employee code and date
    List<AttendanceDynamo> findByEmployeeCodeAndDate(String employeeCode, String date);
}
