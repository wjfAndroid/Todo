package com.wjf.realmandgreendao;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface UserDao {
    void insert(User user);

    List<User> getAll();

    void delete(int id);

    User upDate(User user);

    User get(int id);
    void  closeRealm();
}
