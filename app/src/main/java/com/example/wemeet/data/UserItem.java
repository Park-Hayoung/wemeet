package com.example.wemeet.data;

import java.io.Serializable;

public class UserItem implements Serializable {
    private String userId;
    private String userPassword;
    private String nickname;
    private String introduce;
    private boolean termsUse;
    private boolean termsNotification;
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

    public boolean isTermsUse() {
        return termsUse;
    }

    public void setTermsUse(boolean termsUse) {
        this.termsUse = termsUse;
    }

    public boolean isTermsNotification() {
        return termsNotification;
    }

    public void setTermsNotification(boolean termsNotification) {
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