package com.tian.myhouse.pojo;

import lombok.Data;

@Data
public class OwnerApply {
    private Integer oaId;
    private String account;//账户
    private String name;//用户真实姓名
    private Integer gender;//用户性别，1男，2女
    private Integer homeId;//用户住址id
    private Integer age;//用户年龄
    private String addressDetail;//详细住址
    private String phone;//用户手机号
    private String vx;//用户微信
    private Integer houseNum;//房屋数量
    private Integer roomNum;//房间数量
    private String description;//其他说明
    private Integer IDpic;//图片数组对应的Id
    private Integer oastate;//申请记录状态
    private String refuseReason;//拒绝理由
}
