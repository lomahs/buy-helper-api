package bhn.buyhelper.service.impl;

import bhn.buyhelper.model.entity.Category;
import bhn.buyhelper.repository.ICategoryRepository;
import bhn.buyhelper.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CategoryService implements IService<Category, String> {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Optional<Category> getById(String key) {
        return Optional.empty();
    }

    @Override
    public void save(Category object) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void update(Category object) {

    }
}