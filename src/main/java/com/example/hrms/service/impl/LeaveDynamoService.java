package com.example.hrms.service.impl;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
import com.example.hrms.entity.dynamo.LeaveStatus;
import com.example.hrms.repository.dynamo.LeaveRequestDynamoRepository;
import com.example.hrms.service.LeaveService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class LeaveDynamoService implements LeaveService {

    private final LeaveRequestDynamoRepository repo;

    public LeaveDynamoService(LeaveRequestDynamoRepository repo) {
        this.repo = repo;
    }


    @Override
    public LeaveRequestDynamo create(LeaveRequestDynamo request) {
        // Auto-generate leaveRequestCode if not provided
        if (request.getLeaveRequestCode() == null || request.getLeaveRequestCode().isEmpty()) {
            String leaveCode = "LEAVE-" + request.getEmployeeCode() + "-" + request.getStartDate();
            request.setLeaveRequestCode(leaveCode);
        }
        
        // Generate hash ID from leaveRequestCode
        if (request.getId() == null || request.getId().isEmpty()) {
            request.setId(request.generateHashId(request.getLeaveRequestCode()));
        }
        
        if (request.getStatus() == null) {
            request.setStatus(LeaveStatus.PENDING);
        }
        request.setTimestamps();
        return repo.save(request);
    }

    @Override
    public List<LeaveRequestDynamo> createBulk(List<LeaveRequestDynamo> requests) {
        List<LeaveRequestDynamo> saved = new ArrayList<>();
        if (requests == null) return saved;
        for (LeaveRequestDynamo r : requests) {
            // Auto-generate leaveRequestCode if not provided
            if (r.getLeaveRequestCode() == null || r.getLeaveRequestCode().isEmpty()) {
                String leaveCode = "LEAVE-" + r.getEmployeeCode() + "-" + r.getStartDate();
                r.setLeaveRequestCode(leaveCode);
            }
            
            // Generate hash ID from leaveRequestCode
            if (r.getId() == null || r.getId().isEmpty()) {
                r.setId(r.generateHashId(r.getLeaveRequestCode()));
            }
            if (r.getStatus() == null) {
                r.setStatus(LeaveStatus.PENDING);
            }
            r.setTimestamps();
            saved.add(repo.save(r));
        }
        return saved;
    }

    @Override
    public LeaveRequestDynamo getById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<LeaveRequestDynamo> getByEmployee(String employeeCode) {
        return repo.findByEmployeeCode(employeeCode);
    }

    @Override
    public List<LeaveRequestDynamo> getByStatus(LeaveStatus status) {
        return repo.findByStatus(status);

    }

    @Override
    public LeaveRequestDynamo updateStatus(String leaveRequestCode, LeaveStatus status, String reason) {
        // Find by leaveRequestCode to get the ID
        Iterable<LeaveRequestDynamo> leavesIterable = repo.findAll();
        String leaveId = null;
        for (LeaveRequestDynamo l : leavesIterable) {
            if (leaveRequestCode.equals(l.getLeaveRequestCode())) {
                leaveId = l.getId();
                break;
            }
        }
            
        if (leaveId == null) return null;
        
        // Use ID to fetch and update
        Optional<LeaveRequestDynamo> opt = repo.findById(leaveId);
        if (!opt.isPresent()) return null;
        
        LeaveRequestDynamo leave = opt.get();
        leave.setStatus(status);
        if (reason != null && !reason.isEmpty()) {
            leave.setReason(reason);
        }
        leave.setTimestamps();
        return repo.save(leave);
    }
    
    @Override
    public LeaveRequestDynamo updateStatusById(String id, LeaveStatus status, String reason) {
        Optional<LeaveRequestDynamo> opt = repo.findById(id);
        if (!opt.isPresent()) return null;
        
        LeaveRequestDynamo leave = opt.get();
        leave.setStatus(status);
        if (reason != null && !reason.isEmpty()) {
            leave.setReason(reason);
        }
        leave.setTimestamps();
        return repo.save(leave);
    }
}


