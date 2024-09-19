package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "assignments")
public class Assignments extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicles vehicle;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Drivers driver;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

}
