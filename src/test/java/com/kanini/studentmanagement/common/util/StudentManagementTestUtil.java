package com.kanini.studentmanagement.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.dto.request.DepartmentRequest;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.dto.intermediate.DepartmentDTO;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

public class StudentManagementTestUtil {
    protected StudentManagementTestUtil(){}
    public static String getContent(ObjectMapper objectMapper, StudentRequest studentRequest)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(studentRequest);
    }

    public static StudentResponse createStubOfStudentResponse() {
        StudentResponse localStudentResponse = StudentResponse.builder()
                .studentName("Indronil Chawkroborty")
                .departmentName("Information Technology")
                .course("Ecommerce")
                .specialization("Spring boot")
                .course("Spring Boot 3.1.11")
                .percentage("80%")
                .build();
        return localStudentResponse;
    }

    public static StudentRequest createStubOfStudentRequest(){
        DepartmentRequest departmentRequest = DepartmentRequest.builder()
                .departmentName("Mathematics Department").build();

        StudentRequest localStudentRequest = StudentRequest.builder()
                .studentName("Indronil Chawkroborty")
                .course("Maths")
                .specialization("Relational Calculus")
                .departmentRequest(departmentRequest)
                .percentage("80%")
                .build();
        return localStudentRequest;
    }

    public static StudentDTO createStubOfStudentDTO(){
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentName("Mathematics Department").build();
        StudentDTO studentDTO = StudentDTO.builder()
                .studentName("Indronil Chawkroborty")
                .course("Maths")
                .specialization("Relational Calculus")
                .departmentDTO(departmentDTO)
                .percentage("80%")
                .build();

        return studentDTO;
     }

}
