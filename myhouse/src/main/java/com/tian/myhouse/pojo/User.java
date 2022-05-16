package com.tian.myhouse.pojo;


/**
 * 用户实体类
 */

public class User {
    private Integer userID;
    private String account;
    private String password;
    private boolean isOwner;//是否为房东

    public User() {
    }

    public User(Integer userID, String account, String password, boolean isOwner) {
        this.userID = userID;
        this.account = account;
        this.password = password;
        this.isOwner = isOwner;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", isOwner=" + isOwner +
                '}';
    }
}
