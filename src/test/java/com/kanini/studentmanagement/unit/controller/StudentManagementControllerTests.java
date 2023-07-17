package com.kanini.studentmanagement.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.controller.StudentManagementController;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.business.sexception.StudentBusinessException;
import com.kanini.studentmanagement.model.dto.intermediate.DepartmentDTO;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.assertThatResponseObjectHasValidData;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.getContent;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentRequest;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentResponse;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentDTO;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.assertThatStudentRequestAndResponseHasSameValuesInFields;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, MockitoTestExecutionListener.class })
@WebMvcTest(controllers = StudentManagementController.class)
public class StudentManagementControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ModelMapper modelMapper;
    @MockBean
    StudentManagementService studentService;
    @InjectMocks
    StudentManagementController studentManagementController;
    StudentRequest studentRequest;
    StudentDTO studentDTO;
    DepartmentDTO departmentDTO;
    StudentResponse studentResponse;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup(){
        studentRequest = createStubOfStudentRequest();
        studentResponse = createStubOfStudentResponse();
        departmentDTO = createStubOfDepartmentDTO();
        studentDTO = createStubOfStudentDTO();
    }

    private DepartmentDTO createStubOfDepartmentDTO() {
        DepartmentDTO localDepartmentDTO = DepartmentDTO.builder()
                .departmentName("Mathematics Department").build();
        return localDepartmentDTO;
    }

    @DisplayName("This unit test case, tests the basic flow of the student registration API to check when a student is provided" +
            "whether it is successfully added by mocking the other components")
    @Test
    public void givenStudentRequest_whenRegisterStudent_thenSendSavedStudent() throws Exception {
        // where the mockbean of instance model mapper is called for conversion of POJO(s)
        when(modelMapper.map(studentRequest, StudentDTO.class)).thenReturn(studentDTO);
        when(modelMapper.map(studentRequest.getDepartmentRequest(), DepartmentDTO.class)).thenReturn(departmentDTO);
        when(modelMapper.map(studentDTO, StudentResponse.class)).thenReturn(studentResponse);
        // given also that we make a call to the registerCustomer of mockbean of
        // customerOnboardingService with the stubbed CustomerDTO
        try {
            when(studentService.registerStudent(studentDTO)).thenReturn(studentDTO);
        } catch (StudentBusinessException e) {
            throw new RuntimeException(e);
        }

        // when the operation we are going to check is performed
        MockHttpServletRequestBuilder mockRequest = post("/api/v1/student/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getContent(objectMapper, studentRequest));

        ResultActions response = mockMvc.perform(mockRequest);

        // then - verify the result with the new bunch of assert statements.
        // these are the new asserts in Junit-5 wrapped with Spring Boot Test
        // for the web layer - which provides validity of the response object
        // returned from service or business layer
        assertThatResponseObjectHasValidData(response, studentResponse);

        assertThatStudentRequestAndResponseHasSameValuesInFields(studentRequest, studentResponse);
    }



}
