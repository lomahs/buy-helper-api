package com.bn.buyerhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDate date;

    private double amount;
}