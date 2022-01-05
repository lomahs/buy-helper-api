package bhn.buyhelper.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bhn.buyhelper.model.entity.Product;
import bhn.buyhelper.repository.ICartRepository;
import bhn.buyhelper.repository.ICategoryRepository;
import bhn.buyhelper.repository.IOrderDetailRepository;
import bhn.buyhelper.repository.IProductRepository;
import bhn.buyhelper.service.IProductService;

@Service
public class ProductService implements IProductService {

    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    IOrderDetailRepository orderDetailRepository;

    @Autowired
    ICartRepository cartRepository;

    @Override
    public Page<Product> getByPage(int page, int itemNumber) {
        return productRepository.findAll(PageRequest.of(page, itemNumber));
    }

    @Override
    public Optional<Product> getById(String key) {
        return productRepository.findById(key);
    }

    @Override
    public void save(Product object) {
        productRepository.saveAndFlush(object);
    }

    private void removeFromCombos(Product product) {
        if (!product.isCombo()) {
            product.getListCombos().forEach(combo -> {
                combo.getListProducts().remove(product);
                save(combo);
            });
        }
    }

    private void removeFromCarts(Product product) {
        product.getListCarts().forEach(cart -> {
            cart.getListProducts().remove(product);
            cartRepository.saveAndFlush(cart);
        });
    }

    private void removeFromOrderDetails(Product product) {
        product.getListOrderDetails().forEach(orderDetail -> {
            orderDetail.setProduct(null);
            orderDetailRepository.saveAndFlush(orderDetail);
        });
    }

    @Override
    @Transactional
    public void delete(String key) {
        Product product = getById(key).get();
        removeFromCombos(product);
        removeFromCarts(product);
        removeFromOrderDetails(product);
        productRepository.deleteById(key);
    }

    @Override
    public void update(Product object) {
        Product product = getById(object.getProductId()).get();
        product.setProductName(object.getProductName());
        product.setPrice(object.getPrice());
        product.setImage(object.getImage());
        product.setQuantity(object.getQuantity());
        product.setDiscount(object.getDiscount());
        product.setListCategories(object.getListCategories());
        product.setListProducts(object.getListProducts());
        save(product);
    }

    @Override
    public List<Product> findProductsInCategoryByName(String name, String id) {
        return productRepository.findProductsByNameAndCategory(name, id);
    }

    @Override
    public List<Product> findByProvider(String id) {
        return productRepository.findByProviderUserId(id);
    }

}
