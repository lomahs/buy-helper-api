package bhn.buyhelper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bhn.buyhelper.model.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.date LIKE %?1% AND o.buyer IN (SELECT u.userId FROM User u WHERE u.userAddress LIKE %?2%)")
    public List<Order> findByDateAndAddress(String date, String userAddress);
}
