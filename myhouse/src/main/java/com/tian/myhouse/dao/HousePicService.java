package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.HousePic;
import org.apache.ibatis.annotations.Param;

public interface HousePicService {

    //添加对应house 的图片数组
    //int addHousePic(@Param("picone") String picone,@Param("pictwo") String pictwo,@Param("picthree")String picthree,@Param("picfour")String picfour,@Param("picfive")String picfive,@Param("houid")Integer houid);

    int addHousePic(HousePic housePic);

    //申请记录添加时更新对应房屋图片数组的houid 通过hpicid查找
    int setHouid(@Param("houid") Integer houid,@Param("hpicid")Integer hpicid);

    //通过hpicid查询房屋图片数组
    HousePic findHousePicByHpicid(@Param("hpicid")Integer hpicid);

    //通过hpicid删除指定房屋的图片数组
    int deleteHousepicByhpicid(@Param("hpicid")Integer hpicid);

}
