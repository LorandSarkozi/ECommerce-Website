package com.example.demo.model;

import com.example.demo.dto.FAQDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public FAQDto getFAQDto(){
        FAQDto faqDto = new FAQDto();
        faqDto.setQuestion(question);
        faqDto.setAnswer(answer);
        faqDto.setId(id);
        faqDto.setProductId(product.getId());

        return faqDto;
    }

}
