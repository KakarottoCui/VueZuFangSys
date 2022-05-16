package com.tian.myhouse.pojo;

public class UserInfo {
    private int isOwner;//用户是否未房东
    private String account;//用户账号
    private Integer pageStart;//当前页
    private Integer pageSize;//每页最大数

    public UserInfo() {
    }

    public UserInfo(int isOwner, String account, Integer pageStart, Integer pageSize) {
        this.isOwner = isOwner;
        this.account = account;
        this.pageStart = pageStart;
        this.pageSize = pageSize;
    }

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "isOwner=" + isOwner +
                ", account='" + account + '\'' +
                ", pageStart=" + pageStart +
                ", pageSize=" + pageSize +
                '}';
    }
}
