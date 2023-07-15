package com.kanini.studentmanagement.model.data.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class Audit implements Serializable {
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name="created_At", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name="updated_At", nullable = false)
    private OffsetDateTime updatedAt;
}
