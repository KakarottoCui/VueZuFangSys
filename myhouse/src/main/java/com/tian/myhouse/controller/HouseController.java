package com.tian.myhouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tian.myhouse.dao.HouseDao;
import com.tian.myhouse.dao.HousePicService;
import com.tian.myhouse.dao.OwnerRecordService;
import com.tian.myhouse.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class HouseController {

    @Resource
    private HouseDao houseDao;

    @Resource
    private HousePicService housePicService;

    @Resource
    private OwnerRecordService ownerRecordService;

    @RequestMapping("/addHouseUpRecord")
    public String addHouseUpRecord(@RequestParam("houseUpForm") String houseUpForm) {

        HouseInfo houseInfo = JSONObject.parseObject(houseUpForm,HouseInfo.class);

        //将房屋户型数组，房屋地区代码数组，配套设施列表数组 转化为string类型
        String houseTypeJson = JSON.toJSONString(houseInfo.getHouseType());
        String houseIDJson = JSON.toJSONString(houseInfo.getHouseID());
        String checkListJson = JSON.toJSONString(houseInfo.getCheckList());

        //创建一个长度为五的数组存放图片
        String[] houpicStr = {"","","","",""};
        for (int i = 0; i < houseInfo.getHousePic().length ; i++) {

            houpicStr[i] = houseInfo.getHousePic()[i];
        }

        HousePic housePic = new HousePic(houpicStr[0],houpicStr[1],houpicStr[2],houpicStr[3],houpicStr[4]);

        //存图片数组，hpicid存在housePic对象中
        int addHousePic = housePicService.addHousePic(housePic);

        if (addHousePic < 1){
            return "fail";
        }

        House house = new House(houseInfo.getAccount(),houseTypeJson,houseIDJson,houseInfo.getRentType(),houseInfo.getRentArea(),houseInfo.getFloor(),houseInfo.getAddressDetail(),checkListJson,houseInfo.getMonthRent(),houseInfo.getDescription(),housePic.getHpicid());

        //int addhouid = houseDao.addHouseUpRecord(houseInfo.getAccount(),houseTypeJson,houseIDJson,houseInfo.getRentType(),houseInfo.getRentArea(),houseInfo.getFloor(),houseInfo.getAddressDetail(),checkListJson,houseInfo.getMonthRent(),houseInfo.getDescription(),hpicid);
        //存申请总记录
        int addHouseUpRecord = houseDao.addHouseUpRecord(house);
        if(addHouseUpRecord < 1){
            return "fail";
        }

        //更新对应房屋表中的房屋图片数组对应行记录中 houid
        int end = housePicService.setHouid(house.getHouid(),housePic.getHpicid());
        

        //System.out.println(housePic.getHpicid());

//        System.out.println(houseTypeJson);
//
//        String[] aa = JSONObject.parseObject(houseTypeJson,String[].class);
//
//        for (String a:aa
//             ) {
//            System.out.println(a);
//        }
        // System.out.println(houseUpForm);
        return end == 1? "success":"fail";
    }

    @RequestMapping("/getAllHURecords")
    public String getAllHURecords(HURecordsInfo huRecordsInfo){
        int HURecords;//返回查询到的记录数
        Integer pageStart;
        List<House> houseList;
        List<HouseInfo> houseInfoArrayList = new ArrayList<>();
        pageStart = (huRecordsInfo.getPageStart()-1)*huRecordsInfo.getPageSize();

        //查询记录数量
        HURecords = houseDao.getAllHURecordsNum("%"+huRecordsInfo.getAccount()+"%");
        houseList = houseDao.getAllHUList("%"+huRecordsInfo.getAccount()+"%",pageStart,huRecordsInfo.getPageSize());

        for (House hu:houseList
        ) {
            String[] houseType = JSONObject.parseObject(hu.getHouseType(),String[].class);
            String[] houseID = JSONObject.parseObject(hu.getHouseID(),String[].class);
            String[] checkList = JSONObject.parseObject(hu.getCheckList(),String[].class);
            HousePic houseP = housePicService.findHousePicByHpicid(hu.getHousePic());
            String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};

            houseInfoArrayList.add(new HouseInfo(hu.getHouid(),hu.getAccount(),houseType,houseID,hu.getRentType(),hu.getRentArea(),hu.getFloor(),hu.getAddressDetail(),checkList,hu.getMonthRent(),hu.getDescription(),housePic,hu.getState(),hu.getRefuseReason()));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("HURecords",HURecords);//存放查询结果用户总个数
        res.put("houseInfoArrayList",houseInfoArrayList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串
        return res_json;
    }

    //获取所有展示中的房源信息(分页查询)
    @RequestMapping("/getAllOnshowHURecords")
    public String getAllOnshowHURecords(HURecordsInfo huRecordsInfo){
        int HURecords;//返回查询到的记录数
        Integer pageStart;
        List<House> houseList;
        List<HouseInfo> houseInfoArrayList = new ArrayList<>();
        pageStart = (huRecordsInfo.getPageStart()-1)*huRecordsInfo.getPageSize();

        //查询记录数量
        HURecords = houseDao.getAllOnshowHURecordsNum("%"+huRecordsInfo.getAccount()+"%");
        houseList = houseDao.getAllOnshowHUList("%"+huRecordsInfo.getAccount()+"%",pageStart,huRecordsInfo.getPageSize());

        for (House hu:houseList
        ) {
            String[] houseType = JSONObject.parseObject(hu.getHouseType(),String[].class);
            String[] houseID = JSONObject.parseObject(hu.getHouseID(),String[].class);
            String[] checkList = JSONObject.parseObject(hu.getCheckList(),String[].class);
            HousePic houseP = housePicService.findHousePicByHpicid(hu.getHousePic());
            String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};

            houseInfoArrayList.add(new HouseInfo(hu.getHouid(),hu.getAccount(),houseType,houseID,hu.getRentType(),hu.getRentArea(),hu.getFloor(),hu.getAddressDetail(),checkList,hu.getMonthRent(),hu.getDescription(),housePic,hu.getState(),hu.getRefuseReason()));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("HURecords",HURecords);//存放查询结果用户总个数
        res.put("houseInfoArrayList",houseInfoArrayList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串
        return res_json;
    }

    //条件筛选房源信息
    @RequestMapping("/getAllOnshowHURecordsByKey")
    public String getAllOnshowHURecordsByKey(@RequestParam("houseKey")String houseKey,@RequestParam("rentArea")String rentArea,@RequestParam("monthRent")String monthRent,@RequestParam("houseType")String houseType0,@RequestParam("rentType")String rentType,@RequestParam("floor")String floor,@RequestParam("pageStart")Integer pageStart,@RequestParam("pageSize")Integer pageSize){
        int rentAreaStart = Integer.parseInt(rentArea);
        int monthRentStart = Integer.parseInt(monthRent);
        //int houseTypeNum = Integer.parseInt(houseType0);
        int rentTypeNo = Integer.parseInt(rentType);
        int floorStart = Integer.parseInt(floor);

        int HURecords;//返回查询到的记录数
        Integer pageStartTemp;
        List<House> houseList;
        List<HouseInfo> houseInfoArrayList = new ArrayList<>();
        pageStartTemp = (pageStart-1)*pageSize;

        //查询记录数量
        HURecords = houseDao.getAllHURecordsByKeysNum("%"+houseKey+"%",rentAreaStart,monthRentStart,rentTypeNo,floorStart);
        houseList = houseDao.getAllHUListByKeys("%"+houseKey+"%",rentAreaStart,monthRentStart,rentTypeNo,floorStart,pageStartTemp,pageSize);

        for (House hu:houseList
        ) {
            String[] houseType = JSONObject.parseObject(hu.getHouseType(),String[].class);
            String[] houseID = JSONObject.parseObject(hu.getHouseID(),String[].class);
            String[] checkList = JSONObject.parseObject(hu.getCheckList(),String[].class);
            HousePic houseP = housePicService.findHousePicByHpicid(hu.getHousePic());
            String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};

            houseInfoArrayList.add(new HouseInfo(hu.getHouid(),hu.getAccount(),houseType,houseID,hu.getRentType(),hu.getRentArea(),hu.getFloor(),hu.getAddressDetail(),checkList,hu.getMonthRent(),hu.getDescription(),housePic,hu.getState(),hu.getRefuseReason()));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("HURecords",HURecords);//存放查询结果用户总个数
        res.put("houseInfoArrayList",houseInfoArrayList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串
        return res_json;



//        System.out.println("rentAreaStart："+rentAreaStart);
//        System.out.println("monthRentStart："+monthRentStart);
//        System.out.println("houseTypeNum："+houseTypeNum);
//        System.out.println("rentTypeNum："+rentTypeNum);
//        System.out.println("floorStart："+floorStart);

    }

    //获取所有未通过的房屋信息列表
    @RequestMapping("/gethouseUpRecord")
    public String getHouseUpRecords(HURecordsInfo huRecordsInfo){
        int HURecords;//返回查询到的记录数
        Integer pageStart;
        List<House> houseList;
        List<HouseInfo> houseInfoArrayList = new ArrayList<>();
        pageStart = (huRecordsInfo.getPageStart()-1)*huRecordsInfo.getPageSize();

        //查询记录数量
        HURecords = houseDao.getDesignatedAllHURecordsNum("%"+huRecordsInfo.getAccount()+"%");
        houseList = houseDao.getDesignatedAllHUList("%"+huRecordsInfo.getAccount()+"%",pageStart,huRecordsInfo.getPageSize());

        for (House hu:houseList
             ) {
            String[] houseType = JSONObject.parseObject(hu.getHouseType(),String[].class);
            String[] houseID = JSONObject.parseObject(hu.getHouseID(),String[].class);
            String[] checkList = JSONObject.parseObject(hu.getCheckList(),String[].class);
            HousePic houseP = housePicService.findHousePicByHpicid(hu.getHousePic());
            String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};

            houseInfoArrayList.add(new HouseInfo(hu.getHouid(),hu.getAccount(),houseType,houseID,hu.getRentType(),hu.getRentArea(),hu.getFloor(),hu.getAddressDetail(),checkList,hu.getMonthRent(),hu.getDescription(),housePic,hu.getState()));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("HURecords",HURecords);//存放查询结果用户总个数
        res.put("houseInfoArrayList",houseInfoArrayList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串

        return res_json;
    }

    //删除某条申请记录
    @RequestMapping("/deleteOneHouseUpRecord")
    public String deleteOneHouseUpRecord(@RequestParam("houid") Integer houid){
        int hpicid = houseDao.findHousepicByHouid(houid);
        int deleteHpicidFlag = housePicService.deleteHousepicByhpicid(hpicid);
        if (deleteHpicidFlag < 1){
            return "fail";
        }
        int deleteHouidFlag = houseDao.deleteOneHouseUpRecord(houid);

        return deleteHouidFlag == 1 ? "success":"fail";
    }

    //通过申请，房屋记录state值设置为2通过（也是上架方法）
    @RequestMapping("/submitHouseUpRecord")
    public String submitHouseUpRecord(@RequestParam("houid")Integer houid){
        int submit = houseDao.submitHouseUpRecord(houid);
        return submit == 1 ? "success":"fail";
    }

    //下架房屋，房屋记录state值设置为1
    @RequestMapping("/offHouse")
    public String offHouseUpRecord(@RequestParam("houid")Integer houid){
        int off = houseDao.offHouseUpRecord(houid);
        return off == 1 ? "success":"fail";
    }

    //拒绝申请
    @RequestMapping("/refuseHouseUpRecord")
    public String refuseHouseUpRecord(@RequestParam("houid")Integer houid,@RequestParam("refusereason")Integer refusereason){

        int refuse = houseDao.refuseHouseUpRecord(houid,refusereason);

        return refuse > 0 ? "success":"fail";
    }

    //房东用户关键字搜索房源信息接口
    @RequestMapping("/getAccBykeyHURecords")
    public String getAccBykeyHURecords(FindbykeyInfo findbykeyInfo){
        int HURecords;//返回查询到的记录数
        Integer pageStart;
        List<House> houseList;
        List<HouseInfo> houseInfoArrayList = new ArrayList<>();
        pageStart = (findbykeyInfo.getPageStart()-1)*findbykeyInfo.getPageSize();

        //查询记录数量
        HURecords = houseDao.getKeyAllHURecordsNum(findbykeyInfo.getAccount(),"%"+findbykeyInfo.getKey()+"%");
        houseList = houseDao.getKeyAllHUList(findbykeyInfo.getAccount(),"%"+findbykeyInfo.getKey()+"%",pageStart,findbykeyInfo.getPageSize());

        for (House hu:houseList
        ) {
            String[] houseType = JSONObject.parseObject(hu.getHouseType(),String[].class);
            String[] houseID = JSONObject.parseObject(hu.getHouseID(),String[].class);
            String[] checkList = JSONObject.parseObject(hu.getCheckList(),String[].class);
            HousePic houseP = housePicService.findHousePicByHpicid(hu.getHousePic());
            String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};

            houseInfoArrayList.add(new HouseInfo(hu.getHouid(),hu.getAccount(),houseType,houseID,hu.getRentType(),hu.getRentArea(),hu.getFloor(),hu.getAddressDetail(),checkList,hu.getMonthRent(),hu.getDescription(),housePic,hu.getState()));
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("HURecords",HURecords);//存放查询结果用户总个数
        res.put("houseInfoArrayList",houseInfoArrayList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串

        return res_json;
    }

    //查询指定houid的
    @RequestMapping("/getHouseDetailByhouid")
    public String getHouseDetailByhouid(@RequestParam("houid")Integer houid){
        House house = houseDao.getHouseDetailByhouid(houid);
        HousePic houseP = housePicService.findHousePicByHpicid(house.getHousePic());
        String[] housePic = {houseP.getPicone(),houseP.getPictwo(),houseP.getPicthree(),houseP.getPicfour(),houseP.getPicfive()};
        HouseInfo houseInfo = new HouseInfo(house.getHouid(),house.getAccount(),JSONObject.parseObject(house.getHouseType(),String[].class),JSONObject.parseObject(house.getHouseID(),String[].class),house.getRentType(),house.getRentArea(),house.getFloor(),house.getAddressDetail(),JSONObject.parseObject(house.getCheckList(),String[].class),house.getMonthRent(),house.getDescription(),housePic,house.getState(),house.getRefuseReason());
        System.out.println(houid);
        String account = houseDao.getAccountByHouid(houid);
        OwnerApply ownerApply = ownerRecordService.findApplyRecordByAccount(account);

        HashMap<String, Object> res = new HashMap<>();
        res.put("house",houseInfo);
        res.put("owner",ownerApply);
        String house_json = JSON.toJSONString(res);
        return house_json;
    }

}
