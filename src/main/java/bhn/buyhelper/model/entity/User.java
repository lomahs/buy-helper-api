package bhn.buyhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.CartView;
import bhn.buyhelper.view.OrderView;
import bhn.buyhelper.view.UserView;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(generator = "userIdGen")
    @GenericGenerator(name = "userIdGen", parameters = @Parameter(name = "prefix", value = "U"), strategy = "bhn.buyhelper.utils.IdGenerator")
    @JsonView(value = { CartView.BaseView.class, UserView.BaseView.class, OrderView.AllView.class })
    private String userId;

    @JsonView(value = { UserView.BaseView.class, OrderView.AllView.class })
    @Size(max = 30, min = 1, message = "name must be at least 1 character, maximum 30 characters")
    @NotBlank(message = "name is only whitespace")
    private String userName;

    @JsonView(value = { UserView.BaseView.class, OrderView.AllView.class })
    private String userAddress;

    @JsonView(value = { UserView.BaseView.class, OrderView.AllView.class })
    private String phone;

    @OneToOne
    @JoinColumn(name = "account")
    private Account account;

    @JsonView(value = { UserView.BaseView.class })
    private String userAvatar;

    @OneToOne
    @JoinColumn(name = "cart")
    private Cart cart;

    @OneToMany(mappedBy = "buyer")
    private Set<Order> listOrderOfBuyers;

    @OneToMany(mappedBy = "provider")
    private Set<Order> listOrderOfProviders;

    @OneToMany(mappedBy = "provider")
    private Set<Product> listProducts;
}