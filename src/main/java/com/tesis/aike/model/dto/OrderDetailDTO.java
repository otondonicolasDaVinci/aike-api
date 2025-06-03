package com.tesis.aike.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDTO {
    private Long id;
    private Long productId; // Solo el ID del producto
    private String productTitle; // Quizás el nombre para mostrar
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal; // quantity * unitPrice

    // Getters y setters generados por Lombok
}