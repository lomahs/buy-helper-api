package bhn.buyhelper.service;

import java.util.List;

import bhn.buyhelper.model.entity.Order;
import bhn.buyhelper.model.entity.User;

public interface IOrderService extends IService<Order, String> {
    public Order order(Order order, User user);

    public List<Order> getOrderByDateAndAddress(String date, String address);
}
