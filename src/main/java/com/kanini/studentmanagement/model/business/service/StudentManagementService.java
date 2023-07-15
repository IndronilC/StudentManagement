package com.kanini.studentmanagement.model.business.service;

import com.kanini.studentmanagement.model.business.sexception.StudentBusinessException;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

public interface StudentManagementService {
    public StudentDTO registerStudent(StudentDTO studentDTO) throws StudentBusinessException;
}
