package com.example.demo.model;

import com.example.demo.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table( name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    public ProductDto getDto(){
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setByteImg(img);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;
    }

}
