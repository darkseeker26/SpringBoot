package com.exercisetwo.exercisetwo.repositories;

import com.exercisetwo.exercisetwo.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {

}