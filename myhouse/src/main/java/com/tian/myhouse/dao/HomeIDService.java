package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.HomeID;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeIDService {
    //添加homeid
    int addHomeID(@Param("province") String province, @Param("city") String city, @Param("area") String area,@Param("account")String account);

    //获取指定用户的申请记录中的homeid数组的hid
    int findhidByAccount(@Param("account")String account);

    //删指定账户的家庭住址代号
    int deleteHomeIDByAccount(@Param("account")String account);

    //通过account查询指定账户的homeid
    HomeID findHomeIDByAccount(@Param("account")String account);

    //通过hid查询指定账户的homeid
    HomeID findHomeIDByhid(@Param("hid")int hid);

    //更新某账号的HomeID
    int updateHomeID(@Param("province") String province, @Param("city") String city, @Param("area") String area,@Param("account")String account);
}
