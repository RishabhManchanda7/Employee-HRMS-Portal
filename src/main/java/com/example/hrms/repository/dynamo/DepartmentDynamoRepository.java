package com.example.hrms.repository.dynamo;

import com.example.hrms.entity.dynamo.DepartmentDynamo;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface DepartmentDynamoRepository extends DynamoDBPagingAndSortingRepository<DepartmentDynamo, String> {
    
    // Find by department code
    Optional<DepartmentDynamo> findByDepartmentCode(String departmentCode);
    
    // Find by name
    List<DepartmentDynamo> findByName(String name);
    
    // Find by location
    List<DepartmentDynamo> findByLocation(String location);
}
