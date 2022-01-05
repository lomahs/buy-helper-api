package bhn.buyhelper.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import bhn.buyhelper.model.entity.Account;
import bhn.buyhelper.model.entity.Cart;
import bhn.buyhelper.model.entity.User;
import bhn.buyhelper.repository.IAccountRepository;
import bhn.buyhelper.repository.ICartRepository;
import bhn.buyhelper.repository.IUserRepository;
import bhn.buyhelper.service.IAccountService;

@Service
public class AccountService implements IAccountService {

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ICartRepository cartRepository;

    @Override
    public Page<Account> getByPage(int page, int itemNumber) {
        return null;
    }

    @Override
    public Optional<Account> getById(String key) {
        return accountRepository.findById(key);
    }

    @Override
    @Transactional
    public void save(Account object) {
        Account account = accountRepository.saveAndFlush(object);
        Cart cart = cartRepository.saveAndFlush(Cart.builder().build());
        userRepository.saveAndFlush(User.builder()
                .account(account)
                .cart(cart)
                .userName(account.getUsername())
                .build());
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void update(Account object) {
        Account account = getById(object.getUsername()).get();
        account.setPassword(object.getPassword());
        save(account);
    }

}
