package bhn.buyhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bhn.buyhelper.model.entity.Account;
import bhn.buyhelper.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    public User findByAccount(Account account);

}
