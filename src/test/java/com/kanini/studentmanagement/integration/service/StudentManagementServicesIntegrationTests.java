package com.kanini.studentmanagement.integration.service;

import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.data.repository.AuditRepository;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

import static com.kanini.studentmanagement.common.util.StudentManagementTestUtil.createStubOfStudentDTO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;



/**
 * <p>the {@code StudentManagementServicesIntegrationTests} is using the
 * {@link org.springframework.test.context.TestExecutionListeners} annotation to
 * inject the TestExecution Listener classes which is available from version
 * <em>Spring boot 3.0.8</em> onwards.
 * </p>
 * <p> The above is not present by default as Spring boot 3.0.8 has inducted a new feature
 * called <b>Observability</b> through which as an <em>AOP Advise</em> Test Cases
 * are also monitored thus we need to add the sub classes of <b>TestExecutionListeners</b>
 * interfaces which are added through the said annotation, else we get some <em>ClassCastException</em>
 * in the test console as a <em>warning</em>. Also without them the <b>Spring Application Context</b>
 * may not be available to load the {@code Autowired} <em>Pojos</em>
 * </p>
 */

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StudentManagementServicesIntegrationTests {
    @Autowired
    StudentManagementService studentManagementService;
    @Autowired
    StudentManagementRepository studentManagementRepository;
    @Autowired
    AuditRepository auditRepository;
    @Autowired
    StudentDTO studentDTO;

    /**
     * <p>The {@code setUp} method is a <em>>test fixture</em> which is used to set up the data and re-use
     * for other unit integration tests.
     * </p>
     * <p>First we are setting up the {@link com.kanini.studentmanagement.model.dto.intermediate.StudentDTO}
     * to ensure that there is a stub which is programmatically setup to add the {@code studentDTO} parameter
     * which the controller has to send to get the <em>Student data Created</em> in the Database
     * </p>
     * <p>The {@code StudentManagementServicesIntegrationTests} being a proxy to the controller layer to test
     * the actual integration behaviour with service, repository and database, needs the stub of
     * {@code studentDTO} to start the <em>create / register Student behaviour</em>
     * </p>
     * <p> Both the repositories are calling delete method and we are using {@code BeforeEach} so that <b>before
     * each integration test case run</b>, we can create the stub {@code studentDTO} and <em>then clean the Mysql Database
     * of all the previous data to run the new test case</em>, this is a convention and a best practice of running such
     * unit or integration testcases
     * </p>
     */
    @BeforeEach
    public void setUp(){
       studentDTO = createStubOfStudentDTO();
       auditRepository.deleteAll(); // will have to check if this is really necessary.
       studentManagementRepository.deleteAll();
    }

    @DisplayName("This test is to check the happy path for Student Registration from the Service Layer" +
            "to the Database through Repository Layer as a part of Unit Integration Test of the Service +" +
            " Repository + Database Resource")
    @Test
    public void givenStudentObject_whenRegisterStudentInDatabase_thenReturnedSavedStudent() throws Exception{
        StudentDTO savedStudentDTO = studentManagementService.registerStudent(studentDTO);
        assertCreateStudentIsCorrectlyDone(savedStudentDTO);
    }

    private void assertCreateStudentIsCorrectlyDone(StudentDTO savedStudentDTO) {
        assertThat(savedStudentDTO).isNotNull();
        assertThat(savedStudentDTO.getStudentName()).isEqualTo(studentDTO.getStudentName());
        assertThat(savedStudentDTO.getCourse()).isEqualTo(studentDTO.getCourse());
        assertThat(savedStudentDTO.getPercentage()).isEqualTo(studentDTO.getPercentage());
        assertThat(savedStudentDTO.getSpecialization()).isEqualTo(studentDTO.getSpecialization());
        assertThatCreatedStudentHasTheCorrectDepartmentValues(savedStudentDTO);
    }

    private void assertThatCreatedStudentHasTheCorrectDepartmentValues(StudentDTO savedStudentDTO) {
        assertThat(savedStudentDTO.getDepartmentDTO().getDepartmentName())
                .isEqualTo(studentDTO.getDepartmentDTO().getDepartmentName());
    }


}
