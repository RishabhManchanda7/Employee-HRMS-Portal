package com.example.hrms.service.impl;

import com.example.hrms.entity.dynamo.LeaveRequestDynamo;
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
        if (request.getId() == null || request.getId().isEmpty()) {
            String base = request.getEmployeeId() + "-" + request.getStartDate();
            request.setId("LEAVE-" + base);
        }
        if (request.getStatus() == null || request.getStatus().isEmpty()) {
            request.setStatus("PENDING");
        }
        request.setTimestamps();
        return repo.save(request);
    }

    @Override
    public List<LeaveRequestDynamo> createBulk(List<LeaveRequestDynamo> requests) {
        List<LeaveRequestDynamo> saved = new ArrayList<>();
        if (requests == null) return saved;
        for (LeaveRequestDynamo r : requests) {
            if (r.getId() == null || r.getId().isEmpty()) {
                String base = r.getEmployeeId() + "-" + r.getStartDate();
                r.setId("LEAVE-" + base);
            }
            if (r.getStatus() == null || r.getStatus().isEmpty()) {
                r.setStatus("PENDING");
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
    public List<LeaveRequestDynamo> getByEmployee(String employeeId) {
        return repo.findByEmployeeId(employeeId);
    }

    @Override
    public List<LeaveRequestDynamo> getByStatus(String status) {
        return repo.findByStatus(status);
    }

    @Override
    public LeaveRequestDynamo approve(String id) {
        Optional<LeaveRequestDynamo> opt = repo.findById(id);
        if (!opt.isPresent()) return null;
        LeaveRequestDynamo r = opt.get();
        r.setStatus("APPROVED");
        r.setTimestamps();
        return repo.save(r);
    }

    @Override
    public LeaveRequestDynamo reject(String id, String reason) {
        Optional<LeaveRequestDynamo> opt = repo.findById(id);
        if (!opt.isPresent()) return null;
        LeaveRequestDynamo r = opt.get();
        r.setStatus("REJECTED");
        if (reason != null && !reason.isEmpty()) {
            r.setReason(reason);
        }
        r.setTimestamps();
        return repo.save(r);
    }
}


