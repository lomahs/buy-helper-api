package com.bn.buyerhelper.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(generator = "orderIdGen")
    @GenericGenerator(
            name = "orderIdGen",
            parameters = @Parameter(name = "prefix", value = "ORD"),
            strategy = "com.bn.buyerhelper.utils.IdGenerator"
    )
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "proivder_id")
    private User provider;

    private LocalDate date;

    private double totalMoney;

    private boolean isProviderConfirm;

    private boolean isTownLeaderCheck;

    private boolean isBuyerReceive;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    @JsonBackReference
    private Set<OrderDetail> listOrderDetails;

    @OneToOne(mappedBy = "order")
    @JsonBackReference
    private Payment payment;
}