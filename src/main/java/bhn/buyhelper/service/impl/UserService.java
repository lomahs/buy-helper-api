package bhn.buyhelper.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bhn.buyhelper.model.entity.Account;
import bhn.buyhelper.model.entity.Cart;
import bhn.buyhelper.model.entity.User;
import bhn.buyhelper.repository.ICartRepository;
import bhn.buyhelper.repository.IUserRepository;
import bhn.buyhelper.service.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ICartRepository cartRepository;

    @Override
    public Page<User> getByPage(int page, int itemNumber) {
        return userRepository.findAll(PageRequest.of(page, itemNumber));
    }

    @Override
    public Optional<User> getById(String key) {
        return userRepository.findById(key);
    }

    @Override
    public void save(User object) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void update(User object) {
        User user = getById(object.getUserId()).get();
        user.setUserName(object.getUserName());
        user.setUserAddress(object.getUserAddress());
        user.setPhone(object.getPhone());
        user.setUserAvatar(object.getUserAvatar());
        save(user);
    }

    @Override
    public User getUserByAccount(Account account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public Cart updateCart(Cart cart) {
        cartRepository.saveAndFlush(cart);
        return null;
    }

}
