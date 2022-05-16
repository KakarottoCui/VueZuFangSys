package com.tian.myhouse.dao;

import com.tian.myhouse.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

//管理员接口
@Repository
public interface AdminDao {
    Admin getAdmin(@Param("phone") String phone,@Param("password") String password);
}
