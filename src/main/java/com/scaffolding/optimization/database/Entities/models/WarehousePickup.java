package com.scaffolding.optimization.database.Entities.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.sql.ast.tree.update.Assignment;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "warehouse_pickups")
public class WarehousePickup {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouses warehouse;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignments assignment;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

}
