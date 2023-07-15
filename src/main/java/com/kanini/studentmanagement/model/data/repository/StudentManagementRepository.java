package com.kanini.studentmanagement.model.data.repository;

import com.kanini.studentmanagement.model.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentManagementRepository extends JpaRepository<Student, UUID> {
}
