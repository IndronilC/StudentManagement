package com.kanini.studentmanagement.controller;

import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.business.service.StudentService;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class StudentManagementController {

    @Autowired
    StudentService studentService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/student/register")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse registerStudent(@RequestBody StudentRequest studentRequest){
        StudentDTO studentDTO = mapToStudentDTOFromRequest(studentRequest);
        StudentDTO savedStudentDTO = studentService.registerStudent(studentDTO);
        StudentResponse studentResponse = mapToResponseFromStudentDTO(studentDTO);
        return new StudentResponse();
    }

    private StudentResponse mapToResponseFromStudentDTO(StudentDTO studentDTO) {
        StudentResponse studentResponse = modelMapper.map(studentDTO, StudentResponse.class);
        return studentResponse;
    }

    private StudentDTO mapToStudentDTOFromRequest(StudentRequest studentRequest) {
        StudentDTO studentDTO = modelMapper.map(studentRequest, StudentDTO.class);
        return studentDTO;
    }

}
