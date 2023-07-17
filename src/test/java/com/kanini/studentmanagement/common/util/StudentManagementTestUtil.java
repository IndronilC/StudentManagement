package com.kanini.studentmanagement.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.dto.request.DepartmentRequest;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.dto.intermediate.DepartmentDTO;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentManagementTestUtil {
    protected StudentManagementTestUtil(){}
    public static String getContent(ObjectMapper objectMapper, StudentRequest studentRequest)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(studentRequest);
    }

    public static StudentResponse createStubOfStudentResponse() {
        StudentResponse localStudentResponse = StudentResponse.builder()
                .studentName("Indronil Chawkroborty")
                .departmentName("Mathematics Department")
                .course("Maths")
                .specialization("Relational Calculus")
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

    public static void assertThatStudentRequestAndResponseHasSameValuesInFields(
            StudentRequest studentRequest, StudentResponse studentResponse) {
        assertEquals(studentRequest.getStudentName(), studentResponse.getStudentName());
        assertEquals(studentRequest.getDepartmentRequest()
                .getDepartmentName(), studentResponse.getDepartmentName());
        assertEquals(studentRequest.getCourse(), studentResponse.getCourse());
        assertEquals(studentRequest.getPercentage(), studentResponse.getPercentage());
        assertEquals(studentRequest.getSpecialization(), studentResponse.getSpecialization());
    }

    public static void assertThatResponseObjectHasValidData(
            ResultActions response, StudentResponse studentResponse) throws Exception {
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.studentName", is(studentResponse.getStudentName())))
                .andExpect(jsonPath("$.departmentName", is(studentResponse.getDepartmentName())))
                .andExpect(jsonPath("$.course", is(studentResponse.getCourse())))
                .andExpect(jsonPath("$.specialization", is(studentResponse.getSpecialization())))
                .andExpect(jsonPath("$.percentage", is(studentResponse.getPercentage())));
    }

}
