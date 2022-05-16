package com.tian.myhouse.pojo;

/**
 * 管理员用户实体
 */
public class Admin {
    private Integer adminID;
    private String phone;
    private String password;

    public Admin() {
    }

    public Admin(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminID=" + adminID +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
