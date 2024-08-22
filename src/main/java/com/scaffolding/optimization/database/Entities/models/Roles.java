package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "role")
    private List<RolesModules> rolesModules = new ArrayList<>();

}
