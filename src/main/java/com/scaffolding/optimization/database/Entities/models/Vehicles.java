package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicles {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_weight", nullable = false)
    private BigDecimal maxWeight;

    @Column(name = "min_weight", nullable = false)
    private BigDecimal minWeight;

    @Column(name = "description")
    private String description;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "stop_limit")
    private Long stopLimit;

    @Column(name = "activation_cost")
    private BigDecimal activationCost;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "gas_type_id", referencedColumnName = "id")
    private Gastypes gasType;

}
