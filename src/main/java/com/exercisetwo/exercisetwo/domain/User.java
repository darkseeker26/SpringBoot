package com.exercisetwo.exercisetwo.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name = "password")
    private String password;

   @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
   @Builder.Default
   private List<Address> addresses = new ArrayList<>();
   public void addAddress(Address address) {
       addresses.add(address);
       address.setUser(this);
   }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }


    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
   private Set<Tag> tags = new HashSet<>();

   public void addTag(String tagName) {
       var tag = new Tag(tagName);
       tags.add(tag);
       tag.getUsers().add(this);
   }

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;

   @ManyToMany
   @JoinTable(
           name = "wishlist",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "product_id")
   )
    private Set<Product> wishlistProducts = new HashSet<>();

   public void addFavoriteProduct(Product product) {
       wishlistProducts.add(product);
   }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "email = " + email + ")";
    }


}
