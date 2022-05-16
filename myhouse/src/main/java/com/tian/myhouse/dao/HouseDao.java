package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseDao {

    //添加上传房屋申请记录
    //int addHouseUpRecord(@Param("account") String account,@Param("houseType") String houseType,@Param("houseID") String houseID,@Param("rentType")Integer rentType,@Param("rentArea")Integer rentArea,@Param("floor")Integer floor,@Param("addressDetail")String addressDetail,@Param("checkList")String checkList,@Param("monthRent")Integer monthRent,@Param("description")String description,@Param("housePic")Integer housePic);
    int addHouseUpRecord(House house);

    //获取所有房源信息包括审核通过的，未审核的，拒绝状态的 的总数
    int getAllHURecordsNum(@Param("account")String account);

    //获取所有展示中的房源信息
    List<House> getAllHUList(@Param("account")String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);

    //获取所有展示中的房源信息的总数
    int getAllOnshowHURecordsNum(@Param("account")String account);

    //获取所有房源信息包括审核通过的，未审核的，拒绝状态的
    List<House> getAllOnshowHUList(@Param("account")String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);


    //查询所有房源信息列表数量，有入参账号就查询对应账号下的所有房源数量
    int getDesignatedAllHURecordsNum(@Param("account") String account);

    //分页查询h
    List<House> getDesignatedAllHUList(@Param("account")String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);

    //关键词查询指定房源信息数量
    int getAllHURecordsByKeysNum(@Param("houseKey")String houseKey,@Param("rentAreaStart")Integer rentAreaStart,@Param("monthRentStart")Integer monthRentStart,@Param("rentTypeNo")Integer rentTypeNo,@Param("floorStart")Integer floorStart);

    //关键词查询指定房源信息所有数据集合list
    List<House> getAllHUListByKeys(@Param("houseKey")String houseKey,@Param("rentAreaStart")Integer rentAreaStart,@Param("monthRentStart")Integer monthRentStart,@Param("rentTypeNo")Integer rentTypeNo,@Param("floorStart")Integer floorStart,@Param("pageStartTemp")Integer pageStart,@Param("pageSize")Integer pageSize);

    //通过地址关键字模糊搜索指定用户的对应记录的数量
    int getKeyAllHURecordsNum(@Param("account") String account,@Param("key")String key);

    //通过地址关键字模糊搜索指定用户的对应记录
    List<House> getKeyAllHUList(@Param("account") String account,@Param("key")String key,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);

    //查询指定houid的房屋详情
    House getHouseDetailByhouid(@Param("houid")Integer houid);

    //通过houid查找某条记录的housepic
    int findHousepicByHouid(@Param("houid")Integer houid);

    //删除指定houid 的房屋记录
    int deleteOneHouseUpRecord(@Param("houid") Integer houid);

    //审核通过state值设为2
    int submitHouseUpRecord(@Param("houid")Integer houid);

    //下架state值设为1
    int offHouseUpRecord(@Param("houid")Integer houid);

    //审核拒绝state值设置为-1
    int refuseHouseUpRecord(@Param("houid")Integer houid,@Param("refusereason")Integer refusereason);

    //查询指定houid的账户account
    String getAccountByHouid(@Param("houid")Integer houid);
}
