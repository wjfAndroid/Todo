package com.wjf.realmandgreendao;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Administrator on 2016/11/11.
 */
public class User extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String name;

    private int age;

    private String hobbies;

   private  Integer money;

    private String dog;

    public User(int id, String name, int age, String hobbies, Integer money, String dog) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.money = money;
        this.dog = dog;
    }

    public User() {
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getDog() {
        return dog;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", hobbies='" + hobbies + '\'' +
                ", money=" + money +
                ", dog='" + dog + '\'' +
                '}';
    }
}
