package com.kanini.studentmanagement.model.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>The {@code Audit} entity class has been extended to ensure
 * that the {@code Department} entity has <em>Auditable columns</em> which are
 * required to check and monitor the data creation and updation date and time and
 * also created by whom or which user.
 * <p>{@code @Version} has been added to enable <em>Optimistic Locking</em> through
 * the version which is an Integer Data type. This will enable
 * <em>concurrent DML or CRUD operation</em> for a <em>Multi User System</em></p>
 * </p>
 * @author - Indronil Chawkroborty
  */

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "student", callSuper = false)
@ToString(exclude = {"student"})
@Table(name = "department")
public class Department extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "department_id", nullable = false)
    private UUID departmentId;
    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @Version
    private Integer version;
}
