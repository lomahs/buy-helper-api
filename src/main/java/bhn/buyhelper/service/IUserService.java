package bhn.buyhelper.service;

import bhn.buyhelper.model.entity.Account;
import bhn.buyhelper.model.entity.Cart;
import bhn.buyhelper.model.entity.User;

public interface IUserService extends IService<User, String> {
    public User getUserByAccount(Account account);

    public Cart updateCart(Cart cart);
}
