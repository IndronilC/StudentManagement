package com.kanini.studentmanagement.integration.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.dto.response.StudentResponse;
import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;

import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentRequest;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentResponse;
import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.getContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p> the following <en>{@code @TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
 *         DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class }) }</em>
 *     has been implemented to ensure that the {@code @TestExecutionListeners} does not look
 *     for {@code MicroMeterTestExecutionListeners} for activating Spring observability based on and from
 *     <em>Spring boot 3.0.0+</em>
 *  </p>
 */

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentManagementControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StudentManagementService studentService;

    @Autowired
    StudentManagementRepository studentManagementRepository;

    @Autowired
    StudentRequest studentRequest;

    @Autowired
    StudentResponse studentResponse;

    @BeforeEach
    public void setup(){
       // given or pre - condtion -> when we populate the request and response stubs
       studentRequest = createStubOfStudentRequest();
       studentResponse = createStubOfStudentResponse();
       studentManagementRepository.deleteAll();

    }

    @DisplayName("This Integration Test will check whether the Rest Endpoint for Student Registration has been actually" +
            "created in the Presentation Layer for actual Rest Method Call")
    @Test
    public void givenStudentObject_whenRegisterStudentEndPointIsValid_thenReceiveOkAsStatus() throws Exception {

        // when the operation we are going to check is performed -> which is a post call for Student Registration
        MockHttpServletRequestBuilder mockRequest = post("/api/v1/student/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getContent(objectMapper, studentRequest));
        ResultActions response = mockMvc.perform(mockRequest);

        // then - verify the result with the new bunch of assert statements whether we receiving a success status
        // i.e whether the API end point is available for the actual call for Registration,
        // this being a TDD.
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("This is a TDD level integration test case which we will allow us to assemble and implement all the" +
            "Components and methods which will allow registration of a new student")
    @Test
    public void givenValidStudentObject_whenRegisterStudentSuccess_thenReturnSavedStudentFromDatabase() throws Exception {

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
                .andExpect(jsonPath("$.departnmentName", is(studentResponse.getDepartmentName())))
                .andExpect(jsonPath("$.course", is(studentResponse.getCourse())))
                .andExpect(jsonPath("$.specialization", is(studentResponse.getSpecialization())))
                .andExpect(jsonPath("$.percentage", is(studentResponse.getPercentage())));

    }


}
