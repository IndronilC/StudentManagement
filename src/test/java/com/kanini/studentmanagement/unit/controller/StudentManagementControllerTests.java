package com.kanini.studentmanagement.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.controller.StudentManagementController;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.business.service.StudentService;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.getContent;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentRequest;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentResponse;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentDTO;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    StudentService studentService;
    @InjectMocks
    StudentManagementController studentManagementController;
    StudentRequest studentRequest;
    StudentDTO studentDTO;
    StudentResponse studentResponse;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup(){
        studentRequest = createStubOfStudentRequest();
        studentResponse = createStubOfStudentResponse();
        studentDTO = createStubOfStudentDTO();
    }

    @DisplayName("This unit test case, tests the basic flow of the student registration API to check when a student is provided" +
            "whether it is successfully added by mocking the other components")
    @Test
    public void givenStudentRequest_whenRegisterStudent_thenSendSavedStudent() throws Exception {
        // where the mockbean of instance model mapper is called for conversion of POJO(s)
        when(modelMapper.map(studentRequest, StudentDTO.class)).thenReturn(studentDTO);
        when(modelMapper.map(studentDTO, StudentResponse.class)).thenReturn(studentResponse);
        // given also that we make a call to the registerCustomer of mockbean of
        // customerOnboardingService with the stubbed CustomerDTO
        when(studentService.registerStudent(studentDTO)).thenReturn(studentDTO);

        // when the operation we are going to check is performed
        MockHttpServletRequestBuilder mockRequest = post("/api/v1/student/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getContent(objectMapper, studentRequest));

        ResultActions response = mockMvc.perform(mockRequest);

        // then - verify the result with the new bunch of assert statements.
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.studentName", is(studentResponse.getStudentName())))
                .andExpect(jsonPath("$.departmentName", is(studentResponse.getDepartmentName())))
                .andExpect(jsonPath("$.course", is(studentResponse.getCourse())))
                .andExpect(jsonPath("$.specialization", is(studentResponse.getSpecialization())))
                .andExpect(jsonPath("$.percentage", is(studentResponse.getPercentage())));

        assertThatStudentRequestAndResponseHasSameValuesInFields();
    }

    private void assertThatStudentRequestAndResponseHasSameValuesInFields() {
        assertEquals(studentRequest.getStudentName(), studentResponse.getStudentName());
        assertEquals(studentRequest.getDepartmentRequest()
                .getDepartmentName(), studentResponse.getDepartmentName());
        assertEquals(studentRequest.getCourse(), studentResponse.getCourse());
        assertEquals(studentRequest.getPercentage(), studentResponse.getPercentage());
        assertEquals(studentRequest.getSpecialization(), studentResponse.getSpecialization());
    }

}
