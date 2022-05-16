package com.tian.myhouse.mapper;

import com.tian.myhouse.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface userMapper {

    @Select("select * from user where name like '%{value}%'")
    public List<User> listUserByName(String name);
}
