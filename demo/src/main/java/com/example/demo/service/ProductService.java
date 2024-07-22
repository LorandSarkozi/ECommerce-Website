package com.example.demo.service;

import com.example.demo.dto.ProductDetailDto;
import com.example.demo.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductByName(String name);
    boolean deleteProduct(Long id);
    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    ProductDetailDto getProductDetailById(Long productId);
}
