package com.scaffolding.optimization.database.Entities.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "role")
    private List<RolesModules> rolesModules = new ArrayList<>();

}
