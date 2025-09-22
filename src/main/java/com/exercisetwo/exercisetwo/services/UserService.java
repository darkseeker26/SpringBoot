package com.exercisetwo.exercisetwo.services;

import com.exercisetwo.exercisetwo.domain.Address;
import com.exercisetwo.exercisetwo.domain.Product;
import com.exercisetwo.exercisetwo.domain.User;
import com.exercisetwo.exercisetwo.repositories.*;
import com.exercisetwo.exercisetwo.repositories.specifications.ProductSpec;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service

public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final EntityManager entityManager;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;


    @Transactional
    public void showEntityStates(){

        var user = User.builder()
                .name("John")
                .email("John@gmail.com")
                .password("1234")
                .build();

        if (entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");

        userRepository.save(user);

        if (entityManager.contains(user))
            System.out.println("Persistent");
        else
            System.out.println("Transient / Detached");
    }

    @Transactional
    public void showRelatedEntities(){
        var profile = profileRepository.findById(2L).orElseThrow();
        System.out.println(profile.getUser().getEmail());
    }

    public void fetchAddress(){
        var address = addressRepository.findById(1L).orElseThrow();
    }

    public void persistRelated(){
        var user = User.builder()
                .name("John")
                .email("john@gmail.com")
                .password("123")
                .build();
        var address = Address.builder()
                .street("example street")
                .city("example city")
                .zip("2400")
                .build();

        user.addAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void deleteRelated(){
        var user = userRepository.findById(3L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }


    @Transactional
    public void manageProducts(){
//        var user = userRepository.findById(2L).orElseThrow();
//        var products = productRepository.findAll();
//
//        products.forEach(user::addFavoriteProduct);
//        userRepository.save(user);
        productRepository.deleteById(5L);
    }

    @Transactional
    public void updateProductPrices(){
        productRepository.updatePriceByCategory(BigDecimal.valueOf(80.99), (byte)1);
    }

    @Transactional
    public void fetchProducts(){
       var product = new Product();
       product.setName("product");

        var matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

       var example = Example.of(product, matcher);
       var products = productRepository.findAll(example);
       products.forEach(System.out::println);
    }

    @Transactional
    public void fetchUser(){
        var user = userRepository.findByEmail("eco@gmail.com").orElseThrow();
        System.out.println(user);
    }

    @Transactional
    public void fetchUsers(){
        var users = userRepository.findAllWithAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    @Transactional
    public void printLoyalProfiles(){
        var users = userRepository.findLoyalUsers(0);
        users.forEach(p -> System.out.println(p.getId() + ": " + p.getEmail()));
    }


//    public void fetchProductByCriteria(){
//        var products = productRepository.findProductsByCriteria("prod", BigDecimal.valueOf(90), null);
//        products.forEach(System.out::println);
//    }

    public void fetchProductsBySpecifications(String name, BigDecimal maxPrice, BigDecimal minPrice){
        Specification<Product> spec = Specification.where(null);

        if(name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if(maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        if(minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchSortedProducts() {
        var sort = Sort.by("name").and
                (Sort.by("price").descending());

        productRepository.findAll(sort).forEach(System.out::println);
    }

    public void fetchPaginatedProducts(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productRepository.findAll(pageRequest);

        var products = page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();
        System.out.printf("Total Pages: %d%n", totalPages);
        System.out.printf("Total Elements: %d%n", totalElements);

    }
}
