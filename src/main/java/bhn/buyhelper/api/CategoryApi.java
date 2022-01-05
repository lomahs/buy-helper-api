package bhn.buyhelper.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import bhn.buyhelper.model.entity.Category;
import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.service.ICategoryService;
import bhn.buyhelper.service.IProductService;
import bhn.buyhelper.view.CategoryView;
import bhn.buyhelper.view.ProductView;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Value("${category.page.items}")
    private int itemNumber;

    @GetMapping
    @JsonView(CategoryView.BaseView.class)
    public ResponseEntity<List<Category>> getCategoriesByPage(@RequestParam(value = "page") int page) {
        Page<Category> categories = categoryService.getByPage(page, itemNumber);
        List<Category> data = categories.get().collect(Collectors.toList());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    @JsonView(CategoryView.BaseView.class)
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") String id) {
        Optional<Category> category = categoryService.getById(id);
        return category.isPresent() ? ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/products")
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<Set<Product>> getProductsByCategoryId(@PathVariable("id") String id) {
        Optional<Category> category = categoryService.getById(id);
        return ResponseEntity.ok(category.get().getListProducts());
    }

    @GetMapping("/{id}/{name}")
    @JsonView(ProductView.AllView.class)
    public ResponseEntity<List<Product>> findProductInCategory(@PathVariable("id") String id,
            @PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findProductsInCategoryByName(name, id));
    }

    @PostMapping
    @JsonView(CategoryView.BaseView.class)
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseEntity.ok(category.getCatId() + " added successfully");
    }

    @PutMapping
    @JsonView(CategoryView.BaseView.class)
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        categoryService.update(category);
        return ResponseEntity.ok(categoryService.getById(category.getCatId()).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") String id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
