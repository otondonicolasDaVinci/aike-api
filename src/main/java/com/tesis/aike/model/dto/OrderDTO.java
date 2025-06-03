package com.tesis.aike.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime; // O String si prefieres manejarlo como String en el DTO
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    private Long userId; // Solo el ID del usuario
    private String userName; // Quizás el nombre del usuario
    private LocalDateTime orderDate; // O String
    private Double totalPrice;
    private String status;
    private List<OrderDetailDTO> orderDetails;

    // Getters y setters generados por Lombok
}