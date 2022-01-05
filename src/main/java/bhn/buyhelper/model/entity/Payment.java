package bhn.buyhelper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.OrderView;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(generator = "paymentIdGen")
    @GenericGenerator(name = "paymentIdGen", parameters = @Parameter(name = "prefix", value = "PMT"), strategy = "bhn.buyhelper.utils.IdGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    @JsonView(value = { OrderView.AllView.class })
    private PaymentMethod paymentMethod;

    private LocalDate date;

    private double amount;
}