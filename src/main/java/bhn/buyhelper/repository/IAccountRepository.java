package bhn.buyhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bhn.buyhelper.model.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

}
