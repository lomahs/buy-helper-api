package bhn.buyhelper.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    private String userName;

    private String userAddress;

    private String phone;

    @OneToOne
    @JoinColumn(name = "account")
    private Account account;

    private String userAvatar;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "buyer")
    @JsonBackReference
    private Set<Order> listOrderOfBuyers;

    @OneToMany(mappedBy = "provider")
    @JsonBackReference
    private Set<Order> listOrderOfProviders;
}