package com.bn.buyerhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(generator = "ordIdGen")
    @GenericGenerator(
            name = "ordIdGen",
            parameters = @Parameter(name = "prefix", value = "OD"),
            strategy = "com.bn.buyerhelper.utils.IdGenerator"
    )
    private String orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}