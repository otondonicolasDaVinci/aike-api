package com.tesis.aike.model.entity;

import jakarta.persistence.*; // O javax.persistence.* si usas una versión anterior de JPA
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O GenerationType.SEQUENCE si prefieres
    private Long id; // Cambiado a Long para ser consistente con otras IDs

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT") // Para descripciones más largas
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(length = 255)
    private String imageUrl; // URL de la imagen del producto

    @Column(length = 100)
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = false) // orphanRemoval = false si no quieres borrar el producto al borrar un detalle
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<OrderDetailEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }
}