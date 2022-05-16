package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.IDpic;
import org.apache.ibatis.annotations.Param;

public interface IDpicService {

    //添加身份证图片数组
    int addIDpic(@Param("picOne")String picOne,@Param("picTwo")String picTwo,@Param("account")String account);

    //通过账号查找
    int findpidByAccount(@Param("account") String account);

    //通过pid查找
    IDpic findpidByPid(@Param("pid")Integer pid);

    //通过账号删除
    int deleteIDpicByAccount(@Param("account")String account);
}
