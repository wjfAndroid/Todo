package com.wjf.realmandgreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        User user1 = new User(22, "tttttt", 88);
//        userDao.insert(user1);
//        for (int i = 0; i < 10; i++) {
//            User user = new User(i + 1, "user" + i, i);
//            userDao.insert(user);
//        }
//        System.out.println("userDao = " + userDao.getAll());
//        user1.setAge(999);
//        userDao.upDate(user1);
//        System.out.println("userDao = " + userDao.getAll());
//        User user = userDao.get(2);
//        System.out.println("user = " + user);
//        userDao.delete(4);
//        System.out.println("userDao = " + userDao.getAll());
        UserDao userDao = new UserDaoImpl(getApplicationContext());

        System.out.println("userDao = " + userDao.getAll());
        userDao.insert(new User(8,"shsdh",6,"shsgh",77,"la"));
        System.out.println("userDao = " + userDao.getAll());

        userDao.closeRealm();

    }
}
