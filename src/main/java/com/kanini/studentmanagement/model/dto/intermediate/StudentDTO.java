package com.kanini.studentmanagement.model.dto.intermediate;

import com.kanini.studentmanagement.dto.request.DepartmentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StudentDTO {
    private String studentName;
    private String course;
    private String specialization;
    private String percentage;
    private DepartmentDTO departmentDTO;

}
