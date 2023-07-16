package com.kanini.studentmanagement.integration.service;

import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StudentManagementServicesIntegrationTests {
    @Autowired
    StudentManagementService studentManagementService;
    @Autowired
    StudentManagementRepository studentManagementRepository;
    @Autowired
    StudentDTO studentDTO;
    @BeforeEach
    public void setUp(){
        studentDTO = createStubOfStudentDTO();
        studentManagementRepository.deleteAll();
    }

    @DisplayName("This test is to check the happy path for Student Registration from the Service Layer" +
            "to the Database through Repository Layer as a part of Unit Integration Test of the Service +" +
            " Repository + Database Resource")
    @Test
    public void givenStudentObject_whenRegisterStudentInDatabase_thenReturnedSavedStudent() throws Exception{
        StudentDTO savedStudentDTO = studentManagementService.registerStudent(studentDTO);


    }




}
