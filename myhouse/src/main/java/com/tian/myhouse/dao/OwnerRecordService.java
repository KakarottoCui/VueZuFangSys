package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.OwnerApply;
import com.tian.myhouse.pojo.OwnerApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.net.Inet4Address;
import java.util.List;

public interface OwnerRecordService {
     //添加一条记录
     int addApplyRecord(@Param("account") String account,@Param("name") String name,@Param("gender") Integer gender,@Param("homeId") Integer homeId,@Param("age") Integer age,@Param("addressdetail") String addressdetail,@Param("phone") String phone,@Param("vx") String vx,@Param("housenum") Integer housenum,@Param("roomnum") Integer roomnum,@Param("description") String description,@Param("idpic") Integer idpic);//添加房东申请记录
     //查询单条记录
     OwnerApply findApplyRecordByAccount(@Param("account") String account);
     //获取总申请记录条数
     int getAllOARecordsNum(@Param("account")String account);
     //查询指定条件列表
     List<OwnerApply> getAllOAList(@Param("account")String account,@Param("pageStart") Integer pageStart,@Param("pageSize") Integer pageSize);
     //删除指定用户申请记录的内容
     int deleteOARecordByAccount(@Param("account")String account);
     //修改记录为已经通过
     int updateOARecord(@Param("account")String account);
     //修改指定账户记录
     int updateOARecordAll(@Param("account")String account,@Param("name")String name,@Param("gender")Integer gender,@Param("homeID")Integer homeID,@Param("age")Integer age,@Param("addressdetail") String addressDetail,@Param("phone") String phone,@Param("vx")String vx,@Param("houseNum")Integer houseNum,@Param("roomNum")Integer roomNum,@Param("description")String description,@Param("IDpic")Integer IDpic);
     //拒绝某条申请
     int refuseOARecord(@Param("account")String account,@Param("refusereason") Integer refusereason);


}
