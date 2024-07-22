package com.example.demo.controller;

import com.example.demo.dto.ProductDetailDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name){
        List<ProductDto> productDtos = productService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId){
        ProductDetailDto productDetailDto = productService.getProductDetailById(productId);
        if(productDetailDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailDto);
    }
}
