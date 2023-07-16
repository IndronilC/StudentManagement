package com.kanini.studentmanagement.model.data.repository;

import com.kanini.studentmanagement.model.data.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID> {
}
