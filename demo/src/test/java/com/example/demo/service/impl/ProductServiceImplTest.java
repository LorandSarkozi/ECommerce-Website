package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FAQRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FAQRepository faqRepository;

    @BeforeEach
    void setUp(){
        initMocks(this);
        productService = new ProductServiceImpl(productRepository,categoryRepository,faqRepository);
    }


    @Test
    public void createProduct_thenFindProduct() {

        Category category1 = new Category();
        category1.setName("Test");
        category1.setDescription("Test");
        //categoryRepository.save(category1);

        Product product = new Product();
        product.setName("TestProduct");
        product.setDescription("adads");
        product.setCategory(category1);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        //productRepository.save(product);
        productService.getProductById(product.getId());
    }

    @Test
    public void deleteProduct_thenFindProduct(){

        Category category1 = new Category();
        category1.setName("Test");
        category1.setDescription("Test");
        categoryRepository.save(category1);

        Product product = new Product();
        product.setName("TestProduct");
        product.setDescription("adads");
        product.setCategory(category1);

        when(productRepository.findAll()).thenReturn(null);

        productService.deleteProduct(product.getId());
        assertNull(productRepository.findAll());
    }

    @Test
    public void givenExistingProduct_whenFindProductById_thenReturnProduct(){

        Category category1 = new Category();
        category1.setName("Test");
        category1.setDescription("Test");
        categoryRepository.save(category1);

        String name = "TestProduct";
        Product product = new Product();
        product.setName(name);
        product.setDescription("adads");
        product.setCategory(category1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto resultProduct = productService.getProductById(1L);

        assertNotNull(resultProduct);
        assertEquals(name, resultProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }
}
