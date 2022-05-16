package com.tian.myhouse.pojo;

import lombok.Data;

@Data
public class OARecordsInfo {
    private String account;//用户账号
    private Integer pageStart;//当前页
    private Integer pageSize;//每页最大数
}
