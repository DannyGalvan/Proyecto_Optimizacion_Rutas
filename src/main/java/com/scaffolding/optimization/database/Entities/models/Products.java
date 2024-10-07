package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Products {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "icon")
    private String icon;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private Classifications classification;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Suppliers supplier;

}
