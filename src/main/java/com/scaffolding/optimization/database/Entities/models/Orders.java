package com.scaffolding.optimization.database.Entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    private Assignments assignment;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name ="address_id", referencedColumnName = "id")
    private Addresses address;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

}
