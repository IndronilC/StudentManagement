package com.kanini.studentmanagement.model.business.service;

import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

public interface StudentService {
    public StudentDTO registerStudent(StudentDTO studentDTO);
}
