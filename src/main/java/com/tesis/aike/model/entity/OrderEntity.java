package com.tesis.aike.model.entity;

import jakarta.persistence.*; // O javax.persistence.*
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // "orders" es un nombre común para la tabla de pedidos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user; // Asumo que tu entidad de usuario se llama UsersEntity

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(length = 50)
    private String status; // Ej: PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    // Helper para añadir detalles al pedido
    public void addOrderDetail(OrderDetailEntity detail) {
        orderDetails.add(detail);
        detail.setOrder(this);
    }

    // Getters y Setters (Lombok @Data los genera)
}