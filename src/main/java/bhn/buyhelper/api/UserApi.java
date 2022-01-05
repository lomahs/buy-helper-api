package bhn.buyhelper.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;

import bhn.buyhelper.model.entity.Account;
import bhn.buyhelper.model.entity.Cart;
import bhn.buyhelper.model.entity.Order;
import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.model.entity.Role;
import bhn.buyhelper.model.entity.User;
import bhn.buyhelper.service.IAccountService;
import bhn.buyhelper.service.IOrderService;
import bhn.buyhelper.service.IProductService;
import bhn.buyhelper.service.IUserService;
import bhn.buyhelper.view.CartView;
import bhn.buyhelper.view.OrderView;
import bhn.buyhelper.view.ProductView;
import bhn.buyhelper.view.UserView;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @Value("${user.page.items}")
    private int itemNumber;

    @GetMapping
    @JsonView(UserView.BaseView.class)
    public ResponseEntity<List<User>> getUserByPage(@RequestParam("page") int page) {
        Page<User> users = userService.getByPage(page, itemNumber);
        List<User> data = users.get().collect(Collectors.toList());
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}/cart")
    @JsonView(CartView.BaseView.class)
    public ResponseEntity<Cart> getCartByUserId(@PathVariable("id") String id) {
        User user = userService.getById(id).get();
        return ResponseEntity.ok(user.getCart());
    }

    @GetMapping("{id}/orderHistory")
    @JsonView(OrderView.BaseView.class)
    public ResponseEntity<Set<Order>> getAllOrdersByUser(@PathVariable("id") String id) {
        User user = userService.getById(id).get();
        if (user.getAccount().getRole().equals(Role.BUYER)) {
            return ResponseEntity.ok(user.getListOrderOfBuyers());
        }
        if (user.getAccount().getRole().equals(Role.PROVIDER)) {
            return ResponseEntity.ok(user.getListOrderOfProviders());
        }
        return null;
    }

    @GetMapping("{id}/products")
    @JsonView(ProductView.BaseView.class)
    public ResponseEntity<List<Product>> getAllProductsByProvider(@PathVariable("id") String id) {
        return ResponseEntity.ok(productService.findByProvider(id));
    }

    @PostMapping("/login")
    @JsonView(UserView.BaseView.class)
    public ResponseEntity<User> login(@RequestBody Account account) {
        Optional<Account> user = accountService.getById(account.getUsername());
        if (user.isPresent()) {
            Account acc = user.get();
            if (account.getPassword().equals(acc.getPassword())) {
                return ResponseEntity.ok(userService.getUserByAccount(acc));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Account account) {
        Optional<Account> user = accountService.getById(account.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("User existed");
        }
        account.setRole(Role.BUYER);
        accountService.save(account);
        return ResponseEntity.ok("Register successfully");
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody Account account) {
        accountService.save(account);
        return ResponseEntity.ok("Add successfully");
    }

    @PostMapping("/{id}/order")
    public ResponseEntity<String> createOrder(@PathVariable("id") String id, @RequestBody Order order) {
        User user = userService.getById(id).get();
        return ResponseEntity.ok(orderService.order(order, user).getOrderId() + " created successfully");
    }

    @PutMapping
    @JsonView(UserView.BaseView.class)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(userService.getById(user.getUserId()).get());
    }

    @PutMapping("{id}/cart")
    @JsonView(CartView.BaseView.class)
    public ResponseEntity<Cart> updateCart(@PathVariable("id") String id, @RequestBody Cart cart) {
        userService.updateCart(cart);
        return ResponseEntity.ok(userService.getById(id).get().getCart());
    }

    @PutMapping("/account")
    public ResponseEntity<String> changePassword(@Valid @RequestBody Account account) {
        accountService.update(account);
        return ResponseEntity.ok("Password is changed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println(errors);
        return errors;
    }
}
