package com.exercisetwo.exercisetwo.repositories;

import com.exercisetwo.exercisetwo.domain.Category;
import com.exercisetwo.exercisetwo.domain.Product;
import com.exercisetwo.exercisetwo.dtos.ProductSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    //String

    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameNotLike(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);


    // Numbers
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    //null
//    List<Product> findByDescriptionNull();
//    List<Product> findByDescriptionNotNull();
//
//    //Multiple
//    List<Product> findByDescriptionNullAndNameNull();

    //Sort (OrderBy)
    List<Product> findByNameOrderByPrice(String name);

    //Limit (Top/First)
    List<Product> findTop5ByNameOrderByPrice(String name);
    List<Product> findTop5ByNameLikeOrderByPrice(String name);

    // Find products whose price are in a given range and sort by name
    // SQL or JPQL


    @Procedure("findProductByPrice")
    List<Product> findProducts( BigDecimal min, BigDecimal max);

    @Query("select count(*) from Product p where p.price between :min and :max")
    long countProducts(@Param("min")  BigDecimal min, @Param("max") BigDecimal max);

    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(BigDecimal newPrice, Byte categoryId);

    @Query("select new com.exercisetwo.exercisetwo.dtos.ProductSummaryDTO(p.id, p.name ) from Product p where p.category = :category")
    List<ProductSummaryDTO> findByCategory(@Param("category") Category category);

    List<Product> Category(Category category);

}