package com.kanini.studentmanagement.model.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * <p>The {@code Audit} entity class has been extended to ensure
 * that the {@code Student} entity has <em>Auditable columns</em> which are
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
@EqualsAndHashCode(exclude = "departments", callSuper = false)
@ToString(exclude = {"departments"})
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id", nullable = false)
    private UUID studentId;
    @Column(name = "student_name", nullable = false)
    private String studentName;
    @Column(name = "course", nullable = false)
    private String course;
    @Column(name = "specialization", nullable = false)
    private String specialization;
    @Column(name="percentage", nullable = false)
    private String percentage;
    @Version
    private Integer version;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Department> departments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_audit_id", referencedColumnName = "audit_id")
    private Audit audit;


}
