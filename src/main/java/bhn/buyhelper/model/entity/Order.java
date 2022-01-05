package bhn.buyhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.OrderView;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(generator = "orderIdGen")
    @GenericGenerator(name = "orderIdGen", parameters = @Parameter(name = "prefix", value = "O"), strategy = "bhn.buyhelper.utils.IdGenerator")
    @JsonView(value = { OrderView.BaseView.class, OrderView.ProgressView.class })
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonView(value = { OrderView.AllView.class })
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonView(value = { OrderView.AllView.class })
    private User provider;

    @JsonView(value = { OrderView.BaseView.class, OrderView.ProgressView.class })
    private LocalDate date;

    @JsonView(value = { OrderView.BaseView.class })
    private double totalMoney;

    @JsonView(value = { OrderView.AllView.class, OrderView.ProgressView.class })
    private boolean isProviderConfirm;

    @JsonView(value = { OrderView.AllView.class, OrderView.ProgressView.class })
    private boolean isTownLeaderCheck;

    @JsonView(value = { OrderView.AllView.class, OrderView.ProgressView.class })
    private boolean isBuyerReceive;

    @Enumerated(EnumType.STRING)
    @JsonView(value = { OrderView.ProgressView.class })
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> listOrderDetails;

    @OneToOne
    @JoinColumn(name = "paymentId")
    @JsonView(value = { OrderView.AllView.class })
    private Payment payment;
}