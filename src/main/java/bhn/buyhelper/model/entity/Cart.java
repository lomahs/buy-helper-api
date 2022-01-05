package bhn.buyhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.CartView;
import bhn.buyhelper.view.UserView;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

        @Id
        @GeneratedValue(generator = "cartIdGen")
        @GenericGenerator(name = "cartIdGen", parameters = @Parameter(name = "prefix", value = "CART"), strategy = "bhn.buyhelper.utils.IdGenerator")
        @JsonView(value = { CartView.BaseView.class, UserView.BaseView.class })
        private String cartId;

        @ManyToMany
        @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
        @JsonView(value = { CartView.BaseView.class, UserView.BaseView.class })
        private Set<Product> listProducts;

}