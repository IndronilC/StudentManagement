package com.kanini.studentmanagement.model.business.service.impl;

import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.business.sexception.StudentBusinessException;
import com.kanini.studentmanagement.model.data.entity.Student;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceImpl implements StudentManagementService {
    @Autowired
    StudentManagementRepository studentManagementRepository;
    @Autowired
    Student student;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public StudentDTO registerStudent(StudentDTO studentDTO) throws StudentBusinessException {
        student = populateStudentEntityFromDTO(studentDTO);
        Student savedStudent = studentManagementRepository.save(student);
        StudentDTO savedStudentDTO = populateStudentDTOFromEntity(savedStudent);
        return savedStudentDTO;
    }

    private StudentDTO populateStudentDTOFromEntity(Student savedStudent) {
        StudentDTO localSavedStudentDTO = modelMapper.map(savedStudent, StudentDTO.class);
        logStudnetDTOConversionFromStudentEntity(savedStudent, localSavedStudentDTO);
        return localSavedStudentDTO;
    }

    private void logStudnetDTOConversionFromStudentEntity(
            Student savedStudent, StudentDTO localSavedStudentDTO) {
        log.info("StudentDTO converted from saved Student = {} -> {}", localSavedStudentDTO, savedStudent);
    }

    private Student populateStudentEntityFromDTO(StudentDTO studentDTO) {
      Student localStudent = modelMapper.map(studentDTO, Student.class);
      logStudentDTOConversionToStudentEntity(studentDTO, localStudent);
      return localStudent;
    }

    private static void logStudentDTOConversionToStudentEntity(
            StudentDTO studentDTO, Student localStudent) {
        log.info("Student converted from StudentDTO = {} -> {}", localStudent, studentDTO);
    }
}
