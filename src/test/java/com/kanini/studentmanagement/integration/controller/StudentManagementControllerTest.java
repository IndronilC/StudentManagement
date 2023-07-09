package com.kanini.studentmanagement.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanini.studentmanagement.dto.request.StudentRequest;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p> the following <en>{@code @TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
 *         DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class }) }</em>
 *     has been implemented to ensure that the {@code @TestExecutionListeners} does not look
 *     for {@code MicroMeterTestExecutionListeners} for activating Spring observability based on and from
 *     <em>Spring boot 3.0.0+</em></p>
 */

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentManagementControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StudentManagementRepository studentManagementRepository;

    @Autowired
    StudentRequest studentRequest;

    @BeforeEach
    public void setup(){
        // given or pre - condtion
         studentRequest = new StudentRequest();
        studentManagementRepository.deleteAll();

    }

    @Test
    public void givenStudentObject_whenRegisterStudentEndPointIsValid_thenReceiveOkAsStatus() throws Exception {

        // when the operation we are going to check is performed
        MockHttpServletRequestBuilder mockRequest = post("/api/v1/student/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getContent(studentRequest));

        ResultActions response = mockMvc.perform(mockRequest);
        // then - verify the result with the new bunch of assert statements.
        response.andDo(print())
                .andExpect(status().isOk());

    }

    private String getContent(StudentRequest studentRequest)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(studentRequest);
    }

}
