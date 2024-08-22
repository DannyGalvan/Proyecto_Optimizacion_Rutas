package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles_modules")
public class RolesModules {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;


    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Modules module;
}
