package com.tian.myhouse.controller;

import com.alibaba.fastjson.JSON;
import com.tian.myhouse.dao.AdminDao;
import com.tian.myhouse.dao.UserDao;
import com.tian.myhouse.pojo.Admin;
import com.tian.myhouse.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    //自动注入管理员
    @Autowired
    private AdminDao adminDao;
    //自动注入普通用户
    @Autowired
    private UserDao userDao;

    @RequestMapping("/adminlogin")
    public String login(@RequestBody Admin admin){
        String loginFlag = "adminLoginFail";
        //调用dao查询数据库
        Admin admin1 = adminDao.getAdmin(admin.getPhone(), admin.getPassword());
        //存放数据，是否查询到的flag，查询到的admin对象
        Map<String,Object> res = new HashMap<>();
        if (admin1 != null){//查询结果不为空
            loginFlag = "adminLoginSuccess";
            res.put("loginFlag",loginFlag);
            res.put("admin1",admin1);
        }else {//查询结果为空
            res.put("loginFlag",loginFlag);
        }
        String res_json = JSON.toJSONString(res);//将map转换为json格式
        return res_json;//返回json字符串
    }



    @RequestMapping("/userlogin")
    public String userLogin(@RequestBody User user){
        String userLoginFlag = "userLoginFail";
        User user1 = userDao.loginFind(user.getAccount(), user.getPassword());
        HashMap<String, Object> res = new HashMap<>();
        if (user1 != null){
            userLoginFlag = "userLoginSuccess";
            res.put("userLoginFlag",userLoginFlag);
            res.put("user",user1);
        }else{
            res.put("userLoginFlag",userLoginFlag);
        }
        //System.out.println("userlogin");
        String res_json =JSON.toJSONString(res);
        return  res_json;
    }

}
