package com.tesis.aike.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY) // Asumiendo que tienes una ProductEntity
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; // Necesitarás una ProductEntity

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice; // Precio al momento de la compra

    public void setOrder(OrderEntity orderEntity) {

    }

    // Getters y Setters (Lombok @Data los genera)
}