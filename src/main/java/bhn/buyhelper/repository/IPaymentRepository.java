package bhn.buyhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bhn.buyhelper.model.entity.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, String> {

}
