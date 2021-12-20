package bhn.buyhelper.service;

import java.util.List;
import java.util.Optional;

public interface IService<T, K> {

    List<T> getAll();

    Optional<T> getById(K key);

    void save(T object);

    void delete(K key);

    void update(T object);
}