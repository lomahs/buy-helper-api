package bhn.buyhelper.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.service.IService;
import bhn.buyhelper.view.ProductView;

@RestController
@RequestMapping("/api/product")
public class ProductApi {
    @Autowired
    IService<Product, String> service;

    @Value("${product.page.items}")
    private int itemNumber;

    @GetMapping
    @JsonView(ProductView.BaseView.class)
    public ResponseEntity<List<Product>> getProductsByPage(@RequestParam(value = "page") int page) {
        Page<Product> products = service.getByPage(page, itemNumber);
        List<Product> data = products.get().collect(Collectors.toList());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Optional<Product> product = service.getById(id);
        return product.isPresent() ? ResponseEntity.ok(product.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        service.save(product);
        return ResponseEntity.ok(product.getProductId() + " addded sucessfully");
    }

    @PutMapping
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        service.update(product);
        return ResponseEntity.ok(service.getById(product.getProductId()).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
