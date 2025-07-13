package com.tesis.aike.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cabins", schema = "public", catalog = "aike")
public class CabinEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "available", nullable = true)
    private Boolean available;
    @Basic
    @Column(name = "image_url", nullable = true, length = -1)
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CabinEntity that = (CabinEntity) o;
        return id == that.id && capacity == that.capacity && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(available, that.available) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, description, available, imageUrl);
    }

    public boolean isPresent() {
        return false;
    }
}
