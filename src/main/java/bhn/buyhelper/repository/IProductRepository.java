package bhn.buyhelper.repository;

import bhn.buyhelper.model.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT * FROM product WHERE product_name LIKE %:name% AND product_id IN (SELECT product_id FROM product_category WHERE category_id = :id)", nativeQuery = true)
    public List<Product> findProductsByNameAndCategory(@Param("name") String name, @Param("id") String id);

    public List<Product> findByProviderUserId(String userId);
}