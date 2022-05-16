package com.tian.myhouse.pojo;

import lombok.Data;

@Data
public class HousePic {
    private Integer hpicid;
    private String picone;
    private String pictwo;
    private String picthree;
    private String picfour;
    private String picfive;
    private Integer houid;

    public HousePic(String picone, String pictwo, String picthree, String picfour, String picfive) {
        this.picone = picone;
        this.pictwo = pictwo;
        this.picthree = picthree;
        this.picfour = picfour;
        this.picfive = picfive;
    }
}
