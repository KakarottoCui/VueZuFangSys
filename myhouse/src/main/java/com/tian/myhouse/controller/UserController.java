package com.tian.myhouse.controller;

import com.alibaba.fastjson.JSON;
import com.tian.myhouse.dao.UserDao;
import com.tian.myhouse.pojo.User;
import com.tian.myhouse.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserDao userDao;

    @RequestMapping("/getuser")
    public String getUsers(UserInfo userInfo){
        int userCounts;//用户账号模糊查询，用户身份,返回查询结果数量
        Integer pageStart;//计算起始数据
        List<User> userList;//获取查询结果用户列表
        pageStart = (userInfo.getPageStart()-1)*userInfo.getPageSize();//起始页面
        if (userInfo.getIsOwner()==-1){
            userCounts = userDao.getAllCounts("%"+userInfo.getAccount()+"%");
            userList = userDao.getAll("%"+userInfo.getAccount()+"%",pageStart,userInfo.getPageSize());
        }else {
            userCounts = userDao.getUserCounts(userInfo.getIsOwner(), "%" + userInfo.getAccount() + "%");
            userList = userDao.getUsers(userInfo.getIsOwner(), "%" + userInfo.getAccount() + "%",
                    pageStart,userInfo.getPageSize());
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("userCounts",userCounts);//存放查询结果用户总个数
        res.put("userList",userList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串
        return res_json;
    }

    @RequestMapping("/updateuserowner")
    public String updateUserOwner(@RequestParam("account")String account){
        //System.out.println(account);
        int ownerChangeFlag = userDao.changUserOwner(account);
        return ownerChangeFlag > 0 ? "success":"fail";
    }

    @RequestMapping("/deleteuser")
    public String deleteUser(@RequestParam("account") String account){
        int deleteUser = userDao.deleteUser(account);
        return deleteUser > 0 ? "success":"fail";
    }

    @RequestMapping("/edituser")
    public String editUser(@RequestParam("account") String account,@RequestParam("password") String password){
        int editUser = userDao.editUser(account, password);
        return editUser > 0 ? "success":"fail";
    }

    @RequestMapping("/adduser")
    public String addUser(@RequestParam("account") String account,@RequestParam("password") String password){
        int isAccountRepeat = userDao.getAllCounts(account);
        if (isAccountRepeat > 0){
            return "fail";
        }
        int addUser = userDao.addUser(account,password);
        //System.out.println(addUser);
        return addUser > 0 ? "success":"fail";
    }
}
