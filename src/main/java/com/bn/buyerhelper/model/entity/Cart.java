package com.bn.buyerhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(generator = "cartIdGen")
    @GenericGenerator(
            name = "cartIdGen",
            parameters = @Parameter(name = "prefix", value = "CART"),
            strategy = "com.bn.buyerhelper.utils.IdGenerator"
    )
    private String cardId;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> listProducts;

    @OneToOne(mappedBy = "cart")
    private User user;
}