package com.kanini.studentmanagement.model.data.repository;

import com.kanini.studentmanagement.model.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentManagementRepository extends JpaRepository<Student, UUID> {
}
