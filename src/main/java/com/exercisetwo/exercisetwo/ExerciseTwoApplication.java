package com.exercisetwo.exercisetwo;

import com.exercisetwo.exercisetwo.domain.*;
import com.exercisetwo.exercisetwo.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;


@SpringBootApplication
public class ExerciseTwoApplication {

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(ExerciseTwoApplication.class, args);

       var repository = context.getBean(UserService.class);
       repository.fetchPaginatedProducts(0, 10);
    }

}
