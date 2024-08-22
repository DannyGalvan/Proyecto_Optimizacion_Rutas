package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "log_header")
public class LogHeader {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "created_at")
    private Date createdAt;

}
