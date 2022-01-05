package bhn.buyhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bhn.buyhelper.model.entity.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, String> {
}
