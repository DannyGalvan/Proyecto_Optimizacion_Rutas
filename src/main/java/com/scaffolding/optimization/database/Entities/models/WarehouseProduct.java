package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "warehouse_product")
public class WarehouseProduct {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouses warehouse;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "partial_stock", nullable = false)
    private int partialStock;

}
