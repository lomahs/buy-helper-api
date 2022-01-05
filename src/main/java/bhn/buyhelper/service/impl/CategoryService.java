package bhn.buyhelper.service.impl;

import bhn.buyhelper.model.entity.Category;
import bhn.buyhelper.repository.ICategoryRepository;
import bhn.buyhelper.repository.IProductRepository;
import bhn.buyhelper.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Category> getByPage(int page, int itemNumber) {
        return categoryRepository.findAll(PageRequest.of(page, itemNumber));
    }

    @Override
    public Optional<Category> getById(String key) {
        return categoryRepository.findById(key);
    }

    @Override
    public void save(Category object) {
        categoryRepository.saveAndFlush(object);
    }

    @Override
    @Transactional
    public void delete(String key) {
        Category category = getById(key).get();
        category.getListProducts().forEach(product -> {
            product.getListCategories().remove(category);
            productRepository.saveAndFlush(product);
        });
        categoryRepository.deleteById(key);
    }

    @Override
    public void update(Category object) {
        Category category = getById(object.getCatId()).get();
        category.setCatName(object.getCatName());
        save(category);
    }

}