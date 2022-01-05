package bhn.buyhelper.model.entity;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.CartView;
import bhn.buyhelper.view.ProductView;

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
        @GenericGenerator(name = "productIdGen", parameters = @Parameter(name = "prefix", value = "P"), strategy = "bhn.buyhelper.utils.IdGenerator")
        @JsonView(value = { ProductView.BaseView.class, CartView.BaseView.class })
        private String productId;

        @JsonView(value = { ProductView.BaseView.class, CartView.BaseView.class })
        private String productName;

        @JsonView(value = { ProductView.BaseView.class, CartView.BaseView.class })
        private int price;

        @JsonView(value = { ProductView.BaseView.class })
        private int quantity;

        @ManyToMany
        @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
        @JsonView(value = { ProductView.AllView.class })
        private Set<Category> listCategories;

        @ManyToOne
        @JoinColumn(name = "provider_id")
        private User provider;

        @JsonView(value = { ProductView.BaseView.class, CartView.BaseView.class })
        private String image;

        @JsonView(value = { ProductView.BaseView.class, CartView.BaseView.class })
        private double discount;

        @JsonView(value = { ProductView.BaseView.class })
        private boolean isCombo;

        @ManyToMany
        @JoinTable(name = "combo", joinColumns = @JoinColumn(name = "combo_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
        @JsonView(value = { ProductView.AllView.class })
        private Set<Product> listProducts;

        @ManyToMany(mappedBy = "listProducts")
        private Set<Product> listCombos;

        @ManyToMany(mappedBy = "listProducts")
        private Set<Cart> listCarts;

        @OneToMany(mappedBy = "product")
        private Set<OrderDetail> listOrderDetails;

        public int finalPrice() {
                return (int) ((1 - discount) * price);
        }
}