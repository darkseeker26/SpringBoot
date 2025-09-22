package com.exercisetwo.exercisetwo.repositories;

import com.exercisetwo.exercisetwo.domain.Product;

import java.math.BigDecimal;
import java.util.List;


public interface ProductCriteriaRepository {
    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);
}
