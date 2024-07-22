package com.example.demo.service;

import com.example.demo.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
