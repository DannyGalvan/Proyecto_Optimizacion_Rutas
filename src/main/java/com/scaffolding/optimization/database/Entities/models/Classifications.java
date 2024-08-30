package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;


@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "classifications")
public class Classifications extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "classification")
    private List<Products> products;

}
