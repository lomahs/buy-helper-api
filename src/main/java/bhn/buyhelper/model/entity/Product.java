package bhn.buyhelper.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Product {

    @Id
    @GeneratedValue(generator = "productIdGen")
    @GenericGenerator(
            name = "productIdGen",
            parameters = @Parameter(name = "prefix", value = "P"),
            strategy = "bhn.buyhelper.utils.IdGenerator"
    )
    private String productId;

    private String productName;

    private int price;

    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> listCategories;

    private String image;

    private double discount;

    private boolean isCombo;

    @ManyToMany
    @JoinTable(
            name = "combo",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "combo_id")
    )
    private Set<Product> listProducts;

    @ManyToMany(mappedBy = "listProducts")
    @JsonBackReference
    private Set<Product> listCombos;

    @ManyToMany(mappedBy = "listProducts")
    @JsonBackReference
    private Set<Cart> listCarts;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<OrderDetail> listOrderDetails;
}