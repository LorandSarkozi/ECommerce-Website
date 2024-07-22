package com.example.demo.repository;


import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findAllByNameContaining(String title);
}
