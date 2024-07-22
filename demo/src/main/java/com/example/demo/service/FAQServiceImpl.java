package com.example.demo.service;

import com.example.demo.dto.FAQDto;
import com.example.demo.model.FAQ;
import com.example.demo.model.Product;
import com.example.demo.repository.FAQRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId,FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
}
