package com.kanini.studentmanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StudentRequest {
    @JsonProperty("student_name")
    private String studentName;
    @JsonProperty("course")
    private String course;
    @JsonProperty("specialization")
    private String specialization;
    @JsonProperty("percentage_aggregate")
    private String percentage;
    @JsonProperty("department_request")
    private DepartmentRequest departmentRequest;
}
