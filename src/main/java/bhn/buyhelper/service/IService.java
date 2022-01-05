package bhn.buyhelper.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

public interface IService<T, K> {

    Page<T> getByPage(int page, int itemNumber);

    Optional<T> getById(K key);

    void save(T object);

    void delete(K key);

    void update(T object);
}