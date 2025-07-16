package com.tesis.aike.service.impl;

import com.tesis.aike.model.dto.ProductDTO;
import com.tesis.aike.model.entity.ProductEntity;
import com.tesis.aike.repository.ProductRepository;
import com.tesis.aike.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException; // O javax.persistence.EntityNotFoundException
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = mapToEntity(productDTO);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return mapToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + productId));
        return mapToDTO(productEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + productId));

        existingProduct.setTitle(productDTO.getTitle());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setStock(productDTO.getStock());

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return mapToDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Producto no encontrado con id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    private ProductDTO mapToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setTitle(productEntity.getTitle());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setImageUrl(productEntity.getImageUrl());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setStock(productEntity.getStock());
        return productDTO;
    }

    private ProductEntity mapToEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        // No seteamos el ID aquí si es para creación, la base de datos lo generará.
        // Si es para actualización, el ID ya vendría en productDTO o se maneja por separado.
        productEntity.setTitle(productDTO.getTitle());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setImageUrl(productDTO.getImageUrl());
        productEntity.setCategory(productDTO.getCategory());
        productEntity.setStock(productDTO.getStock());
        return productEntity;
    }
}