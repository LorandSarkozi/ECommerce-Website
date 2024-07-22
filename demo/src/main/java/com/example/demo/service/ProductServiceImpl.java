package com.example.demo.service;

import com.example.demo.dto.ProductDetailDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.Category;
import com.example.demo.model.FAQ;
import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FAQRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing product-related operations.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;

    /**
     * Adds a new product based on the provided DTO.
     *
     * @param productDto DTO containing product details.
     * @return DTO representing the newly created product.
     * @throws IOException if an I/O error occurs.
     */
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        // Find the category by ID
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    /**
     * Retrieves all products.
     *
     * @return List of all products.
     */
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    /**
     * Retrieves products by name containing the given search term.
     *
     * @param name Search term for product names.
     * @return List of products matching the search term.
     */
    public List<ProductDto> getAllProductByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id ID of the product to delete.
     * @return True if the product was successfully deleted, false otherwise.
     */
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId ID of the product to retrieve.
     * @return DTO representing the product with the given ID, or null if not found.
     */
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getDto();
        } else {
            return null;
        }
    }

    /**
     * Updates a product's details.
     *
     * @param productId  ID of the product to update.
     * @param productDto DTO containing updated product details.
     * @return DTO representing the updated product.
     * @throws IOException if an I/O error occurs.
     */
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(product.getDescription());
            product.setCategory(optionalCategory.get());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();
        } else {
            return null;
        }
    }

    public ProductDetailDto getProductDetailById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);

            ProductDetailDto productDetailDto = new ProductDetailDto();

            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

            return productDetailDto;

        }
        return null;
    }
}
