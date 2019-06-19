package com.chaychan.news.model.entity;

import com.chaychan.news.enum_.GenderEnum;

import java.io.Serializable;

/**
 * 个人信息实体类
 */
public class UserInfo implements Serializable {

    /**
     * userId : 2018111514554801539
     * userName : 安德拉
     * signature : 你若安好便是晴天2222
     * gender : MAN
     * headAvatar : http://img4.duitang.com/uploads/item/201407/16/20140716132526_TcyTY.thumb.600_0.jpeg
     * password : $2a$10$fzzLW9cSv6hWJM69y/wTk.5ToKx0toFetjNFFtxF4oTrghHAzDTwu
     * phone : 18844157372
     * city : 江苏 常州
     * age : 12
     * backgroundWall : http://image.biaobaiju.com/uploads/20180918/15/1537256494-ZnSKMzEoBI.jpeg
     * creationTime : 1542264948000
     * updateTime : 1543815054000
     */

    private String userId;
    private String userName;
    private String signature;
    private GenderEnum gender;
    private String headAvatar;
    private String password;
    private String phone;
    private String city;
    private int age;
    private String backgroundWall;
    private long creationTime;
    private long updateTime;
    private String e_mall;

    public GenderEnum getGender() {
        return gender;
    }

    public String getE_mall() {
        return e_mall;
    }

    public void setE_mall(String e_mall) {
        this.e_mall = e_mall;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBackgroundWall() {
        return backgroundWall;
    }

    public void setBackgroundWall(String backgroundWall) {
        this.backgroundWall = backgroundWall;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
