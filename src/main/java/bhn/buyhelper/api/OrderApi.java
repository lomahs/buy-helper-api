package bhn.buyhelper.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bhn.buyhelper.model.entity.Order;
import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.service.IOrderService;
import bhn.buyhelper.view.OrderView;
import bhn.buyhelper.view.ProductView;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/{id}/products")
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<Set<Product>> getAllProductByOrder(@PathVariable("id") String id) {
        Order order = orderService.getById(id).get();
        Set<Product> data = order.getListOrderDetails().parallelStream()
                .map(detail -> detail.getProduct())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    @JsonView(OrderView.AllView.class)
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {
        Optional<Order> order = orderService.getById(id);
        return order.isPresent() ? ResponseEntity.ok(order.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{date}/{address}")
    public ResponseEntity<List<Order>> findOrderByDateAndAddress(@PathVariable("date") String date,
            @PathVariable("address") String address) {
        return ResponseEntity.ok(orderService.getOrderByDateAndAddress(date, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") String id) {
        orderService.delete(id);
        return ResponseEntity.ok("Cancel successfully");
    }

    @PutMapping
    @JsonView(OrderView.ProgressView.class)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        orderService.update(order);
        return ResponseEntity.ok(orderService.getById(order.getOrderId()).get());
    }
}
