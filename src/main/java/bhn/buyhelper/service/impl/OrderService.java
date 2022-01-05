package bhn.buyhelper.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import bhn.buyhelper.model.entity.Order;
import bhn.buyhelper.model.entity.OrderStatus;
import bhn.buyhelper.model.entity.Payment;
import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.model.entity.User;
import bhn.buyhelper.repository.IOrderDetailRepository;
import bhn.buyhelper.repository.IOrderRepository;
import bhn.buyhelper.repository.IPaymentRepository;
import bhn.buyhelper.repository.IProductRepository;
import bhn.buyhelper.service.IOrderService;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IOrderDetailRepository orderDetailRepository;

    @Autowired
    IPaymentRepository paymentRepository;

    @Autowired
    IProductRepository productRepository;

    @Override
    public Page<Order> getByPage(int page, int itemNumber) {
        return null;
    }

    @Override
    public Optional<Order> getById(String key) {
        return orderRepository.findById(key);
    }

    @Override
    public void save(Order object) {
        orderRepository.saveAndFlush(object);
    }

    @Override
    @Transactional
    public void delete(String key) {
        Order order = getById(key).get();
        orderDetailRepository.deleteAll(order.getListOrderDetails());
        paymentRepository.delete(order.getPayment());
        orderRepository.delete(order);
    }

    @Override
    public void update(Order object) {
        Order order = getById(object.getOrderId()).get();
        order.setBuyerReceive(object.isBuyerReceive());
        order.setProviderConfirm(object.isProviderConfirm());
        order.setTownLeaderCheck(object.isTownLeaderCheck());
        if (object.isBuyerReceive()) {
            order.setStatus(OrderStatus.Completed);
        }
        save(order);
    }

    private Payment createPaymentForOrder(Order order, int totalMoney) {
        Payment orderPayment = order.getPayment();
        return paymentRepository.saveAndFlush(Payment.builder()
                .date(LocalDate.now())
                .amount(totalMoney)
                .paymentMethod(orderPayment.getPaymentMethod())
                .build());
    }

    private int countTotalMoney(Order order) {
        return order.getListOrderDetails().parallelStream()
                .map(detail -> {
                    int quantity = detail.getQuantity();
                    Product product = productRepository.findById(detail.getProduct().getProductId()).get();
                    product.setQuantity(product.getQuantity() - quantity);
                    productRepository.saveAndFlush(product);
                    return product.finalPrice() * quantity;
                })
                .reduce(Integer::sum).get();
    }

    private Order createOrder(Order order, User user, Payment payment) {
        Order o = orderRepository.saveAndFlush(Order.builder()
                .buyer(user)
                .payment(order.getPayment())
                .status(OrderStatus.Pending)
                .date(LocalDate.now())
                .payment(payment)
                .totalMoney(payment.getAmount())
                .build());
        order.getListOrderDetails().forEach(detail -> {
            detail.setOrder(o);
            orderDetailRepository.saveAndFlush(detail);
        });
        return o;
    }

    @Override
    @Transactional
    public Order order(Order order, User user) {
        int totalMoney = countTotalMoney(order);
        Payment payment = createPaymentForOrder(order, totalMoney);
        Order o = createOrder(order, user, payment);
        return o;
    }

    public List<Order> getOrderByDateAndAddress(String date, String address) {
        return orderRepository.findByDateAndAddress(date, address);
    }

}
