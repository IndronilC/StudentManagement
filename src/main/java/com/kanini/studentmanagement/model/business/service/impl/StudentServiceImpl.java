package com.kanini.studentmanagement.model.business.service.impl;

import com.kanini.studentmanagement.common.util.StudentManagementUtil;
import com.kanini.studentmanagement.model.business.service.StudentManagementService;
import com.kanini.studentmanagement.model.business.sexception.StudentBusinessException;
import com.kanini.studentmanagement.model.data.entity.Audit;
import com.kanini.studentmanagement.model.data.entity.Department;
import com.kanini.studentmanagement.model.data.entity.Student;
import com.kanini.studentmanagement.model.data.repository.AuditRepository;
import com.kanini.studentmanagement.model.data.repository.StudentManagementRepository;
import com.kanini.studentmanagement.model.dto.intermediate.StudentDTO;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Slf4j
public class StudentServiceImpl implements StudentManagementService {
    @Autowired
    StudentManagementRepository studentManagementRepository;
    @Autowired
    Student student;
    @Autowired
    Department department;
    @Autowired
    AuditRepository auditRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private Audit audit;

    private static int auditCreationCounter = 0;


    @Transactional(propagation = REQUIRED)
    @Override
    public StudentDTO registerStudent(StudentDTO studentDTO) throws StudentBusinessException {
        StudentDTO savedStudentDTO = null;
        try {
            student = populateStudentEntityFromDTO(studentDTO);
            Student savedStudent = studentManagementRepository.save(student);
            savedStudentDTO = populateStudentDTOFromEntity(savedStudent);
        } catch (Exception e) {
            throw new StudentBusinessException(e.getMessage(), e.getCause());
        }
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
      createOneToManyStudentDepartmentRelationship(studentDTO, localStudent);
      addAuditDataToStudentAndDepartmentEntities(localStudent);
      logStudentDTOConversionToStudentEntity(studentDTO, localStudent);
      return localStudent;
    }

    private void addAuditDataToStudentAndDepartmentEntities(Student localStudent) {
        Optional<Department> localOptionalDepartment = getDepartment(localStudent);
        addAuditDataMembersToDepartmentEntity(localOptionalDepartment);
        addAuditDataMembersToStudentEntity(localStudent);
    }

    private  Optional<Department> getDepartment(Student localStudent) {
        Optional<Department> localOptionalDepartment = localStudent.getDepartments().stream().findFirst();
        return localOptionalDepartment;
    }

    private void addAuditDataMembersToStudentEntity(Student localStudent) {
        Audit audit = createAuditingDataForEntities();
        localStudent.setAudit(audit);
        audit.setStudent(student);
    }

    private Audit createAuditingDataForEntities() {
        if(auditCreationCounter >= 1){
            return audit;
        }
        auditCreationCounter++;
        audit = new Audit();
        audit.setCreatedBy(StudentManagementUtil.setCreatingUser());
        audit.setCreatedAt(StudentManagementUtil.createCurrentDateTime());
        audit.setUpdatedAt(StudentManagementUtil.createCurrentDateTime());
        Audit savedAudit = auditRepository.save(audit);
        return audit;
    }

    private void addAuditDataMembersToDepartmentEntity(Optional<Department> localOptionalDepartment) {
        Department localDepartment = localOptionalDepartment.get();
        Audit audit = createAuditingDataForEntities();
        localDepartment.setAudit(audit);
        audit.setDepartment(department);
    }



    private void createOneToManyStudentDepartmentRelationship(StudentDTO studentDTO, Student localStudent) {
        department = modelMapper.map(studentDTO.getDepartmentDTO(), Department.class);
        Set<Department> setOfDepartments = new HashSet<Department>();
        setOfDepartments.add(department);
        localStudent.setDepartments(setOfDepartments);
        department.setStudent(localStudent);
    }

    private static void logStudentDTOConversionToStudentEntity(
            StudentDTO studentDTO, Student localStudent) {
        log.info("Student converted from StudentDTO = {} -> {}", localStudent, studentDTO);
    }
}
