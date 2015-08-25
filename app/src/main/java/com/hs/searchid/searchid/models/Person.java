package com.hs.searchid.searchid.models;

/**
 * Created by User on 2015/8/25.
 */
public class Person {
    String uid ;
    String name = "test";
    String address ;
    String sex;
    String birthday;

    public Person() {
        this.name = "Unknown";
    }

    public Person(String uid, String address, String sex, String birthday) {
        this.uid = uid;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
