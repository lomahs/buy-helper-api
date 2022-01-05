package bhn.buyhelper.service;

import java.util.List;

import bhn.buyhelper.model.entity.Product;

public interface IProductService extends IService<Product, String> {
    public List<Product> findProductsInCategoryByName(String name, String id);

    public List<Product> findByProvider(String id);

}
