package com.neverdaless.wemeet.data;

import android.util.Log;

import java.io.Serializable;

public class UserItem implements Serializable {
    private int id; // auto increment id in SQLite
    private String userId;
    private String userPassword;
    private String nickname;
    private String introduce;
    private int termsUse;
    private int termsNotification;
    private String country;
    private String phone;
    private int tall;
    private String bodyShape;
    private String education;
    private int age;
    private String job;
    private String drink;
    private String smoke;
    private String userImage1;
    private String userImage2;
    private String userImage3;
    private String userImage4;

    private static final String TAG = "UserItem";

    public Object[] getObjects() {
        Object[] objects = {id, userId, userPassword, nickname, introduce, termsUse, termsNotification,
                            country, phone, tall, bodyShape, education, age, job, drink, smoke,
                            userImage1, userImage2, userImage3, userImage4};

        return objects;
    }

    public void printItems() {
        Log.d(TAG, String.valueOf(id));
        Log.d(TAG, userId);
        Log.d(TAG, userPassword);
        Log.d(TAG, nickname);
        Log.d(TAG, introduce);
        Log.d(TAG, String.valueOf(termsUse));
        Log.d(TAG, String.valueOf(termsNotification));
        Log.d(TAG, country);
        Log.d(TAG, phone);
        Log.d(TAG, String.valueOf(tall));
        Log.d(TAG, bodyShape);
        Log.d(TAG, education);
        Log.d(TAG, String.valueOf(age));
        Log.d(TAG, job);
        Log.d(TAG, drink);
        Log.d(TAG, smoke);
        Log.d(TAG, userImage1);
        if (userImage2 != null) {
            Log.d(TAG, userImage2);
        }
        if (userImage3 != null) {
            Log.d(TAG, userImage3);
        }
        if (userImage4 != null) {
            Log.d(TAG, userImage4);
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getTermsUse() {
        return termsUse;
    }

    public void setTermsUse(int termsUse) {
        this.termsUse = termsUse;
    }

    public int getTermsNotification() {
        return termsNotification;
    }

    public void setTermsNotification(int termsNotification) {
        this.termsNotification = termsNotification;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTall() {
        return tall;
    }

    public void setTall(int tall) {
        this.tall = tall;
    }

    public String getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(String bodyShape) {
        this.bodyShape = bodyShape;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getUserImage1() {
        return userImage1;
    }

    public void setUserImage1(String userImage1) {
        this.userImage1 = userImage1;
    }

    public String getUserImage2() {
        return userImage2;
    }

    public void setUserImage2(String userImage2) {
        this.userImage2 = userImage2;
    }

    public String getUserImage3() {
        return userImage3;
    }

    public void setUserImage3(String userImage3) {
        this.userImage3 = userImage3;
    }

    public String getUserImage4() {
        return userImage4;
    }

    public void setUserImage4(String userImage4) {
        this.userImage4 = userImage4;
    }
}