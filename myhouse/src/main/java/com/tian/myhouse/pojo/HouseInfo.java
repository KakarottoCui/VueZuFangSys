package com.tian.myhouse.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseInfo {
    private Integer houid;//房屋表主键id
    private String account;//房屋所属账户
    private String[] houseType;//房屋户型
    private String[] houseID;//房屋地区代号
    private Integer rentType;//房屋出租类型
    private Integer rentArea;//房屋面积
    private Integer floor;//房屋楼层
    private String addressDetail;//房屋详细地址
    private String[] checkList;//配套设施列表
    private Integer monthRent;//月租金
    private String description;//房屋详情
    private String[] housePic;//房屋照片表对应数组id
    private Integer state;//房屋详情状态
    private Integer refuseReason;//拒绝理由，不一定有值

    public HouseInfo(Integer houid, String account, String[] houseType, String[] houseID, Integer rentType, Integer rentArea, Integer floor, String addressDetail, String[] checkList, Integer monthRent, String description, String[] housePic, Integer state) {
        this.houid = houid;
        this.account = account;
        this.houseType = houseType;
        this.houseID = houseID;
        this.rentType = rentType;
        this.rentArea = rentArea;
        this.floor = floor;
        this.addressDetail = addressDetail;
        this.checkList = checkList;
        this.monthRent = monthRent;
        this.description = description;
        this.housePic = housePic;
        this.state = state;
    }
}
