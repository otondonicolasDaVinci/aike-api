package com.tesis.aike.service;

import com.tesis.aike.model.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO findProductById(Long productId);

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductsByCategory(String category);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);
}