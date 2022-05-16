package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    //条件查询所有用户
    List<User> getAll(@Param("account") String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
    //条件获取总用户数
    int getAllCounts(@Param("account") String account);
    //条件查询指定用户
    List<User> getUsers(@Param("isOwner") int isOwner,@Param("account") String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
    //查询用户总数
    int getUserCounts(@Param("isOwner") int isOwner,@Param("account") String account);
    //改变用户房东权限
    int changUserOwner(@Param("account") String account);
    //删除用户
    int deleteUser(@Param("account") String account);
    //修改用户信息
    int editUser(@Param("account") String account,@Param("password") String password);
    //添加用户
    int addUser(@Param("account") String account,@Param("password") String password);
    //登录查询用户
    User loginFind(@Param("account") String account,@Param("password") String password);
}
