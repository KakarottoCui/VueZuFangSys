package com.tian.myhouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tian.myhouse.dao.HomeIDService;
import com.tian.myhouse.dao.IDpicService;
import com.tian.myhouse.dao.OwnerRecordService;
import com.tian.myhouse.dao.UserDao;
import com.tian.myhouse.pojo.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class OwnerRecordController {

    @Resource
    private UserDao userDao;

    @Resource
    private HomeIDService homeIDService;

    @Resource
    private IDpicService iDpicService;

    @Resource
    private OwnerRecordService ownerRecordService;

    //添加房东申请记录
    @RequestMapping("/ownerapply")
    public String ownerApply(@RequestParam("ownerapply")String ownerApplyInfo){

        //入参合法性判断
        if (ownerApplyInfo == null || "".equals(ownerApplyInfo)){
            return "fail";
        }

        //将传入的申请房东身份表单的JSON字符串转化对应对象
        OwnerApplyInfo oA1 = JSONObject.parseObject(ownerApplyInfo,OwnerApplyInfo.class);
        //判断入参对象中家庭住址代号数量是否为3，和身份证图片数量是否为2
        if (oA1.getHomeId().length != 3 || oA1.getIDpic().length != 2){
            return "fail";
        }
        //添加homeId到数据库表，并返回家庭住址数组id
        int homeId = homeIDService.addHomeID(oA1.getHomeId()[0],oA1.getHomeId()[1],oA1.getHomeId()[2],oA1.getAccount());
        if (homeId < 0){
            return "fail";
        }
        Integer hid = homeIDService.findhidByAccount(oA1.getAccount());

        //添加IDpic到数据库表，并返回身份证图片数组id
        int picId = iDpicService.addIDpic(oA1.getIDpic()[0],oA1.getIDpic()[1],oA1.getAccount());
        if (picId < 0){
            return "fail";
        }
        Integer pid = iDpicService.findpidByAccount(oA1.getAccount());

        //添加申请记录到数据库
        int addOwnerRecord = ownerRecordService.addApplyRecord(oA1.getAccount(),oA1.getName(),
                oA1.getGender(),hid,oA1.getAge(),oA1.getAddressDetail(),oA1.getPhone()
                ,oA1.getVx(),oA1.getHouseNum(),oA1.getRoomNum(),oA1.getDescription(),pid);

        return addOwnerRecord > 0 ? "success":"fail";
    }


    //查询账户是否有申请的记录，判断是否能再次申请
    @RequestMapping("/findOAByac")
    public String findOwnerApplyRecordByAccount(@RequestParam("account") String account){
        OwnerApply ownerApply = ownerRecordService.findApplyRecordByAccount(account);
        HashMap<String, Object> res = new HashMap<>();
        if (ownerApply != null){
          if (ownerApply.getOastate() == 0){
              res.put("oastate",'0');
          }
          if (ownerApply.getOastate() == 1){
              res.put("oastate",'1');

          }if (ownerApply.getOastate() == 2){
              res.put("oastate",'2');
              res.put("refuseNo",ownerApply.getRefuseReason());
          }
      }else{
            //没有记录
            res.put("oastate","null");
        }
        String res_json = JSON.toJSONString(res);
        return res_json;
    }

    //查询所有记录ist
    @RequestMapping("/findAllOAList")
    public String findAllOAList(OARecordsInfo oaRecordsInfo){
        int OARecords;//返回list长度
        Integer pageStart;
        List<OwnerApply> ownerApplyList;
        List<OwnerApplyInfo> ownerApplyInfoList = new ArrayList<>();
        pageStart = (oaRecordsInfo.getPageStart()-1)*oaRecordsInfo.getPageSize();

        //查询记录数量
        OARecords = ownerRecordService.getAllOARecordsNum("%"+oaRecordsInfo.getAccount()+"%");
        ownerApplyList = ownerRecordService.getAllOAList("%"+oaRecordsInfo.getAccount()+"%",pageStart,oaRecordsInfo.getPageSize());

        for ( OwnerApply ow:ownerApplyList
             ) {
            HomeID homeID = homeIDService.findHomeIDByhid(ow.getHomeId());
            String[] hid = new String[3];
            hid[0] = homeID.getProvince();
            hid[1] = homeID.getCity();
            hid[2] = homeID.getArea();

            IDpic iDpic = iDpicService.findpidByPid(ow.getIDpic());
            String[] pid = new String[2];
            pid[0] = iDpic.getPicone();
            pid[1] = iDpic.getPictwo();

            ownerApplyInfoList.add(new OwnerApplyInfo(ow.getAccount(),ow.getName(),ow.getGender(),hid,
                    ow.getAge(),ow.getAddressDetail(),ow.getPhone(),ow.getVx(),ow.getHouseNum(),
                    ow.getRoomNum(),ow.getDescription(),pid));
        }

        HashMap<String, Object> res = new HashMap<>();
        res.put("OARecords",OARecords);//存放查询结果用户总个数
        res.put("ownerApplyList",ownerApplyInfoList);//存放用户列表
        String res_json = JSON.toJSONString(res);//res转化为json字符串
        return res_json;
    }

    @RequestMapping("/deleteRecordByaccount")
    public String deleteRecordByaccount(@RequestParam("account")String account){
        if (homeIDService.deleteHomeIDByAccount(account) != 1){
            return "fail";
        }
        if (iDpicService.deleteIDpicByAccount(account) != 1){
            return "fail";
        }
        if (ownerRecordService.deleteOARecordByAccount(account) != 1){
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/submitApply")
    public String submitApply(@RequestParam("account")String account){
        int submit = userDao.changUserOwner(account);
        return submit > 0 ? "success":"fail";
    }

    @RequestMapping("/findhomeidDetailByAcount")
    public String findDetailByAccount(@RequestParam("account")String account){
        OwnerApply ownerApply = ownerRecordService.findApplyRecordByAccount(account);
        HomeID hidList = homeIDService.findHomeIDByAccount(account);
        System.out.println(hidList);

        String res_json = JSON.toJSONString(hidList);
        return res_json;
    }

    @RequestMapping("/updateOARecord")
    public String updateOARecord(@RequestParam("account")String account){
        int updateOA = ownerRecordService.updateOARecord(account);
        return updateOA > 0 ? "success":"fail";
    }

    //查找单人记录
    @RequestMapping("/findOneOAByAccount")
    public String findOneOAByAccount(@RequestParam("account")String account){
        OwnerApply ow = ownerRecordService.findApplyRecordByAccount(account);

        HomeID homeID = homeIDService.findHomeIDByhid(ow.getHomeId());
        String[] hid = new String[3];
        hid[0] = homeID.getProvince();
        hid[1] = homeID.getCity();
        hid[2] = homeID.getArea();

        IDpic iDpic = iDpicService.findpidByPid(ow.getIDpic());
        String[] pid = new String[2];
        pid[0] = iDpic.getPicone();
        pid[1] = iDpic.getPictwo();


        String res_json = JSON.toJSONString(new OwnerApplyInfo(ow.getAccount(),ow.getName(),ow.getGender(),hid,
                ow.getAge(),ow.getAddressDetail(),ow.getPhone(),ow.getVx(),ow.getHouseNum(),
                ow.getRoomNum(),ow.getDescription(),pid));

        return res_json;
    }

    //修改用户记录
    @RequestMapping("/updateOARecordAll")
    public String updateOARecordAll(@RequestParam("editOAForm")String ownerApplyInfo){
        //入参合法性判断
        if (ownerApplyInfo == null || "".equals(ownerApplyInfo)){
            return "fail";
        }

        //将传入的申请房东身份表单的JSON字符串转化对应对象
        OwnerApplyInfo editOA = JSONObject.parseObject(ownerApplyInfo,OwnerApplyInfo.class);
        //修改对应的
        int edhomeid = homeIDService.updateHomeID(editOA.getHomeId()[0],editOA.getHomeId()[1],editOA.getHomeId()[2],editOA.getAccount());
        if (edhomeid < 1){
            return "fail";
        }

        //获取修改后记录的hid
        int hid = homeIDService.findHomeIDByAccount(editOA.getAccount()).getHid();
        int pid = iDpicService.findpidByAccount(editOA.getAccount());

        int edit = ownerRecordService.updateOARecordAll(editOA.getAccount(),editOA.getName(),editOA.getGender(),hid,editOA.getAge(),editOA.getAddressDetail(),editOA.getPhone(),editOA.getVx(),editOA.getHouseNum(),editOA.getRoomNum(),editOA.getDescription(),pid);

        return edit>0? "success":"fail";
    }

    //拒绝某用户房东认证申请
    @RequestMapping("/refuseOARecord")
    public String refuseOARecord(@RequestParam("account")String account,@RequestParam("refusereason")Integer refusereason){
        int refuseFlag = ownerRecordService.refuseOARecord(account,refusereason);
        return refuseFlag > 0 ? "success":"fail";
    }
}
