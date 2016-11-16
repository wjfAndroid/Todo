package com.wjf.realmandgreendao;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 2016/11/11.
 */
public class UserDaoImpl implements UserDao {
    Context mContext;
    Realm mRealm;

    public UserDaoImpl(Context context) {
        mContext = context;
        mRealm = RealmUtil.getInstance(mContext).getRealm();
    }

    @Override
    public void insert(User user) {
        mRealm.beginTransaction();
        mRealm.insert(user);
        mRealm.commitTransaction();
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        RealmResults<User> results = mRealm.where(User.class).findAll();
        results.sort("id", Sort.DESCENDING);
        users = results;
        return users;
    }

    @Override
    public void delete(int id) {

        User user = mRealm.where(User.class)
                .equalTo("id", id)
                .findFirst();
        mRealm.beginTransaction();
        user.deleteFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public User upDate(User user) {
        mRealm.beginTransaction();
        User user1 = mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();
        return user1;
    }

    @Override
    public User get(int id) {
        User user = mRealm.where(User.class)
                .equalTo("id", id)
                .findFirst();
        return user;
    }
    @Override
    public void closeRealm(){
        mRealm.close();
    }

}
